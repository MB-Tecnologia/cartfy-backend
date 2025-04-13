package com.cartfy.backend.cartfy_backend.services.impls;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.threeten.bp.LocalDateTime;
import org.threeten.bp.format.DateTimeFormatter;

import com.cartfy.backend.cartfy_backend.entities.ProductList;
import com.cartfy.backend.cartfy_backend.models.DB.ListProductDb;
import com.cartfy.backend.cartfy_backend.models.NoSQL.ResponsePostFirebase;
import com.cartfy.backend.cartfy_backend.models.requests.GetFilterModel;
import com.cartfy.backend.cartfy_backend.models.requests.ListProductRequest;
import com.cartfy.backend.cartfy_backend.models.responses.ListProductsResponse;
import com.cartfy.backend.cartfy_backend.models.responses.OperationResponse;
import com.cartfy.backend.cartfy_backend.models.responses.RetrieveResponse;
import com.cartfy.backend.cartfy_backend.repository.ProductListRepository;
import com.cartfy.backend.cartfy_backend.repository.UserRepository;
import com.cartfy.backend.cartfy_backend.services.ProductListService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.Optional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;


@Service
public class ProductListServiceImpl implements ProductListService{

    @Autowired
    private ProductListRepository _productListRepo;

    @Autowired
    private UserRepository _userRepository;
    // TODO: Validar se usuario existe
    
    @Value("${URL_DATABASE}")
    private String URL_DATABASE;
    
    private ObjectMapper mapper;

    WebClient webClient;
    HttpClient client;
    public ProductListServiceImpl() {
        webClient = WebClient.create(URL_DATABASE);

        client = HttpClient.newBuilder().build();
        mapper = new ObjectMapper();
        
    }

    public OperationResponse save(ListProductRequest listProductRequest){
        try {
            var productListDb = _productListRepo.findByName(listProductRequest.name());
            if(!productListDb.isEmpty()){
                return new OperationResponse(false, "Já existe uma lista com esse nome");
            }

            String url = URL_DATABASE + "/" + listProductRequest.user().getId() + ".json";         
    
            var listProductDb = new ListProductDb(listProductRequest.name(), listProductRequest.products());
            String requestBody = mapper.writeValueAsString(listProductDb);            
                                 
            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))    
                .POST(BodyPublishers.ofString(requestBody))    
                .header("Content-Type", "application/json")        
                .build();

            HttpResponse<String> response = client.sendAsync(request, BodyHandlers.ofString()).get();
            
            ResponsePostFirebase responseBody = mapper.readValue(response.body(), ResponsePostFirebase.class);
            
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("uuuu/MM/dd HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();
            
            var productList = ProductList.builder()
                .usuario(listProductRequest.user())
                .name(listProductRequest.name())
                .urlLista(listProductRequest.user().getId() + "/" + responseBody.name() + ".json")
                .dtIncluso(dtf.format(now))
                .dtAlteracao(dtf.format(now))
                .build();
            
            _productListRepo.save(productList);

            return new OperationResponse(true, "Lista salva com sucesso");
                

        } catch (JsonProcessingException e) {     
            // throw e;       
            e.printStackTrace();
            return new OperationResponse(false, "Erro no processamento do JSON");
        } catch (InterruptedException | ExecutionException e) {            
            e.printStackTrace();
            return new OperationResponse(false, "Erro na requisicao async");
        } catch(Exception e){
            e.printStackTrace();    
            return new OperationResponse(false, "Erro: " + e.toString());
        }
        
    }
            

    public OperationResponse update(long idList, ListProductRequest listProductRequest){
        try {
            var productListDb = _productListRepo.findByName(listProductRequest.name());
            if(productListDb.size() != 0 && productListDb.get(0).getIdLista() != idList){
                return new OperationResponse(false, "Já existe uma lista com esse nome");
            }
            var productListDB = _productListRepo.getReferenceById(idList); 
            String url = URL_DATABASE + "/" + productListDB.getUrlLista();         
            
            ListProductDb listProductDb = new ListProductDb(listProductRequest.name(), listProductRequest.products());
            String requestBody = mapper.writeValueAsString(listProductDb);            
            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))    
                .PUT(BodyPublishers.ofString(requestBody))    
                .header("Content-Type", "application/json")        
                .build();


            HttpResponse<String> response = client.sendAsync(request, BodyHandlers.ofString()).get();            

            if(response.statusCode() == 200){
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("uuuu/MM/dd HH:mm:ss");
                LocalDateTime now = LocalDateTime.now();

                productListDB.setName(listProductRequest.name());
                productListDB.setDtAlteracao(dtf.format(now));
                
                _productListRepo.save(productListDB);
    
                return new OperationResponse(true, "Lista salva com sucesso");
            }
            
            return new OperationResponse(false, "Erro ao salvar lista salva");
                

        } catch (JsonProcessingException e) {            
            e.printStackTrace();
            return new OperationResponse(false, "Erro no processamento do JSON");
        } catch (InterruptedException | ExecutionException e) {            
            e.printStackTrace();
            return new OperationResponse(false, "Erro na requisicao async");
        } catch(Exception e){
            e.printStackTrace();    
            return new OperationResponse(false, "Erro: " + e.toString());
        }
    }

    public OperationResponse delete(long idUser, String listName){
        // TODO: implementar delete
        return null;
    }
    
    public RetrieveResponse<ListProductsResponse> getProductList(long idList){
        var productListDb = _productListRepo.getReferenceById(idList);

        String url = URL_DATABASE + "/" + productListDb.getUrlLista();         

        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(url))    
            .GET()    
            .header("Content-Type", "application/json")        
            .build();

        HttpResponse<String> response;
        try {
            response = client.sendAsync(request, BodyHandlers.ofString()).get();
            var body = response.body();
            if(body.equals("null")){
                return new RetrieveResponse<ListProductsResponse>(false, "A lista nao foi encontrada", null);
            }
            var list = mapper.readValue(response.body(), ListProductsResponse.class);

            return new RetrieveResponse<ListProductsResponse>(true, "", Optional.of(list));
        } catch (InterruptedException | ExecutionException e) {            
            e.printStackTrace();
            return new RetrieveResponse<ListProductsResponse>(false, "Erro na requisicao async", null);        
        } catch (JsonProcessingException e) {            
            e.printStackTrace();
            return new RetrieveResponse<ListProductsResponse>(false, "Erro no processamento do JSON", null);
        }        
    }
    
    public RetrieveResponse<Collection<ListProductsResponse>> getAllProductListByUser(long idUser){        
        String url = URL_DATABASE + "/" + idUser + ".json";         

        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(url))    
            .GET()    
            .header("Content-Type", "application/json")        
            .build();

        HttpResponse<String> response;
        try {
            response = client.sendAsync(request, BodyHandlers.ofString()).get();
            HashMap<String,ListProductsResponse > props;

            // src is a File, InputStream, String or such
            // props = new ObjectMapper().readValue(src, new TypeReference<HashMap<String,Object>>() {});
            // or:
            var body = response.body();
            if(body.equals("null")){
                return new RetrieveResponse<Collection<ListProductsResponse>>(false, "A lista nao foi encontrada", null);
            }
            props = new ObjectMapper().readValue(response.body(), new TypeReference<HashMap<String, ListProductsResponse>>() {});
            
            // for (iterable_type iterable_element : props) {
                
            // }
            
            // var list = mapper.readValue(response.body(), ListProductsResponse.class);

            
            return new RetrieveResponse<Collection<ListProductsResponse>>(true, "", Optional.of(props.values()));
        } catch (InterruptedException | ExecutionException e) {            
            e.printStackTrace();
            return new RetrieveResponse<Collection<ListProductsResponse>>(false, "Erro na requisicao async", null);        
        } catch (JsonProcessingException e) {            
            e.printStackTrace();
            return new RetrieveResponse<Collection<ListProductsResponse>>(false, "Erro no processamento do JSON", null);
        }        
    }

    public RetrieveResponse<Collection<ListProductsResponse>> getProductListByFilter(GetFilterModel filter){
        // TODO implementar metodo no repo para usar o LIKE '%term%' para pesquisar listas

        List<ProductList> productsListDb = new ArrayList<ProductList>();
        if(filter.listName().isPresent()){
            productsListDb = _productListRepo.findByNameContaining(filter.listName().get());
            
        } else{
            productsListDb = _productListRepo.findAll();
        }

        var productsListResponse = new ArrayList<ListProductsResponse>();
        List<CompletableFuture<HttpResponse<String>>> futures = new ArrayList<CompletableFuture<HttpResponse<String>>>();
        for (ProductList productList : productsListDb) {
            String url = URL_DATABASE + "/" + productList.getUrlLista();         
            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))    
                .GET()    
                .header("Content-Type", "application/json")        
                .build();
            HttpResponse<String> response;
            try {
                futures.add(client.sendAsync(request, BodyHandlers.ofString()));

                response = client.sendAsync(request, BodyHandlers.ofString()).get();
                var body = response.body();
                if(body.equals("null")){
                    return new RetrieveResponse<Collection<ListProductsResponse>>(false, "A lista nao foi encontrada", null);
                }
                var list = mapper.readValue(response.body(), ListProductsResponse.class);
                
                productsListResponse.add(list);
            } catch (InterruptedException | ExecutionException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }     catch (JsonProcessingException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }   
            
        }
        if(productsListDb.size() == 0){
            return new RetrieveResponse<Collection<ListProductsResponse>>(false, "Não foram encontrada nenhuma lista", null);
        }
        
        return new RetrieveResponse<Collection<ListProductsResponse>>(true, "", Optional.of(productsListResponse));

        // try {
        // } catch (InterruptedException | ExecutionException e) {            
        //     e.printStackTrace();
        //     return new BaseResponse(false, "Erro na requisicao async", null);        
        // } catch (JsonProcessingException e) {            
        //     e.printStackTrace();
        //     return new BaseResponse(false, "Erro no processamento do JSON", null);
        // }        
    }
    
}

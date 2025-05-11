package com.cartfy.backend.cartfy_backend.services.impls;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.cartfy.backend.cartfy_backend.entities.ProductItem;
import com.cartfy.backend.cartfy_backend.entities.ProductList;
import com.cartfy.backend.cartfy_backend.entities.User;
import com.cartfy.backend.cartfy_backend.models.requests.GetFilterModel;
import com.cartfy.backend.cartfy_backend.models.requests.ListProductRequest;
import com.cartfy.backend.cartfy_backend.models.requests.ProductDto;
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
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
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

    @Value("${URL_DATABASE}")
    private String URL_DATABASE;
    
    WebClient webClient;
    HttpClient client;

    public ProductListServiceImpl() {
        webClient = WebClient.create(URL_DATABASE);

        client = HttpClient.newBuilder().build();
    }

    public OperationResponse save(ListProductRequest listProductRequest){
        try {
            var productListDb = _productListRepo.findByName(listProductRequest.name());
            if(!productListDb.isEmpty()){
                return new OperationResponse(false, "Já existe uma lista com esse nome");
            }
            
            User user = _userRepository.getReferenceById(listProductRequest.idUser());                       
            
            ProductList list = new ProductList();
            list.setName(listProductRequest.name());
            list.setUsuario(user);                        
            
            List<ProductItem> productsDb = mapProductDtoToProductItem(listProductRequest.products(), list);
            list.setProductsItems(productsDb);
                            
            _productListRepo.save(list);

            return new OperationResponse(true, "Lista salva com sucesso");
                
    
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
            
            ProductList  listToUpdate = _productListRepo.findById(idList).get();
            
            
            List<ProductItem> productsDb = mapProductDtoToProductItem(listProductRequest.products(), listToUpdate);           
            
            listToUpdate.getProductsItems().clear();

            listToUpdate.getProductsItems().addAll(productsDb); 
            
            listToUpdate.setName(listProductRequest.name());
            _productListRepo.save(listToUpdate);
    
            return new OperationResponse(true, "Lista salva com sucesso");
        } catch(Exception e){
            e.printStackTrace();    
            return new OperationResponse(false, "Erro: " + e.toString());
        }
    }

    public OperationResponse delete(long idList){        
        try {
            _productListRepo.deleteById(idList);
            return new OperationResponse(true, "Lista deletada com sucesso");
        } catch (Exception e) {
            return new OperationResponse(false, "Erro: " + e.toString());
        }        
    }
    
    public RetrieveResponse<ListProductsResponse> getProductList(long idList){                
        try {
            var productListDb = _productListRepo.findById(idList);

            if(productListDb.isPresent()){
                ListProductsResponse  resp = mapProductsItemsToResponse(productListDb.get().getName(), productListDb.get().getProductsItems());

                return new RetrieveResponse<ListProductsResponse >(true, "", Optional.of(resp));
            }
        } catch (Exception e) {            
            e.printStackTrace();
        }        
        return new RetrieveResponse<ListProductsResponse >(false, "Erro na requisicao async", null);        
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

            var body = response.body();
            if(body.equals("null")){
                return new RetrieveResponse<Collection<ListProductsResponse>>(false, "A lista nao foi encontrada", null);
            }
            props = new ObjectMapper().readValue(response.body(), new TypeReference<HashMap<String, ListProductsResponse>>() {});            
            
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

        return new RetrieveResponse<Collection<ListProductsResponse>>(true, "", Optional.of(null));
    }

    private List<ProductItem> mapProductDtoToProductItem(List<ProductDto> productsDto, ProductList productList){
        
        List<ProductItem> productItems = new ArrayList<>();
        for (ProductDto productDto : productsDto) {
            var p = new ProductItem();

            p.setName(productDto.getName());
            p.setPreco(productDto.getPreco());
            p.setQuantidade(productDto.getQuantidade());
            p.setUrlImg(productDto.getUrlImg());
            p.setLista(productList);                
            p.setGtin(productDto.getGtin());
            
            productItems.add(p);

        }

        return productItems;
    }
    
    private ListProductsResponse mapProductsItemsToResponse(String name, List<ProductItem> productsItems){
        
        List<ProductDto> productDtos = new ArrayList<>();
        for (ProductItem productItem : productsItems) {
            var p = new ProductDto();
            
            p.setName(productItem.getName());
            p.setPreco(productItem.getPreco());
            p.setQuantidade(productItem.getQuantidade());
            p.setUrlImg(productItem.getUrlImg());        
            p.setGtin(productItem.getGtin());               
            
            productDtos.add(p);

        }

        return ListProductsResponse.builder().name(name).products(productDtos).build();        
    }
}

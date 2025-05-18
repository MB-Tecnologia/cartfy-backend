package com.cartfy.backend.cartfy_backend.services.impls;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.cartfy.backend.cartfy_backend.entities.ProductItem;
import com.cartfy.backend.cartfy_backend.entities.ProductList;
import com.cartfy.backend.cartfy_backend.entities.User;
import com.cartfy.backend.cartfy_backend.models.markets.Markets;
import com.cartfy.backend.cartfy_backend.models.requests.GetFilterModel;
import com.cartfy.backend.cartfy_backend.models.requests.ListProductRequest;
import com.cartfy.backend.cartfy_backend.models.requests.ProductDto;
import com.cartfy.backend.cartfy_backend.models.responses.ListProductsResponse;
import com.cartfy.backend.cartfy_backend.models.responses.OperationResponse;
import com.cartfy.backend.cartfy_backend.models.responses.RetrieveResponse;
import com.cartfy.backend.cartfy_backend.models.responses.UserListsProductsResponse;
import com.cartfy.backend.cartfy_backend.repository.ProductListRepository;
import com.cartfy.backend.cartfy_backend.repository.UserRepository;
import com.cartfy.backend.cartfy_backend.services.ProductListService;
import com.cartfy.backend.cartfy_backend.services.marketService.MarketService;
import com.cartfy.backend.cartfy_backend.services.marketService.MarketServiceFactory;

import java.net.http.HttpClient;

import java.util.Optional;
import java.util.ArrayList;
import java.util.List;


@Service
public class ProductListServiceImpl implements ProductListService{

    @Autowired
    private ProductListRepository _productListRepo;

    @Autowired
    private UserRepository _userRepository;

    @Autowired
    private MarketServiceFactory _marketServiceFactory;

    @Value("${URL_DATABASE}")
    private String URL_DATABASE;
    
    WebClient webClient;
    HttpClient client;    

    public ProductListServiceImpl() {
        webClient = WebClient.create(URL_DATABASE);

        client = HttpClient.newBuilder().build();
    }

    @CacheEvict(value = "getAllProductListByUser")
    public OperationResponse save(ListProductRequest listProductRequest){
        try {
            var productListDb = _productListRepo.findByName(listProductRequest.name());
            if(!productListDb.isEmpty()){
                return new OperationResponse(false, "Já existe uma lista com esse nome");
            }
            
            User user = _userRepository.getReferenceById(listProductRequest.idUser());                       
            
            ProductList list = new ProductList();
            list.setName(listProductRequest.name());
            list.setUser(user);                        
            
            List<ProductItem> productsDb = mapProductDtoToProductItem(listProductRequest.products(), list);

            list.setProductsItems(productsDb);
                            
            _productListRepo.save(list);

            return new OperationResponse(true, "Lista salva com sucesso");
                
    
        } catch(Exception e){
            e.printStackTrace();    
            return new OperationResponse(false, "Erro: " + e.toString());
        }
        
    }
            
    @CacheEvict(value = "getAllProductListByUser")
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

    @CacheEvict(value = "getAllProductListByUser")
    public OperationResponse delete(long idList){        
        try {
            _productListRepo.deleteById(idList);
            return new OperationResponse(true, "Lista deletada com sucesso");
        } catch (Exception e) {
            return new OperationResponse(false, "Erro: " + e.toString());
        }        
    }

    // @Cacheable(value = "getProductList")  
    public RetrieveResponse<ListProductsResponse> getProductList(long idList, Markets market){                
        try {
            MarketService service = _marketServiceFactory.getService(market);
            
            Optional<ProductList> productListDb = _productListRepo.findById(idList);
            
            if(productListDb.isPresent()){                                
                List<ProductItem> productsItems = productListDb.get().getProductsItems();
                List<ProductDto> productDtos = service.getProductList(productsItems);


                for (int i = 0; i < productsItems.size(); i++) {
                    productDtos.get(i).setQuantidade(productsItems.get(i).getQuantidade());
                }

                ListProductsResponse resp = ListProductsResponse.builder()
                    .name(productListDb.get().getName())
                    .products(productDtos).build();
                
                return new RetrieveResponse<ListProductsResponse >(true, "", Optional.of(resp));
            }

        } catch (Exception e) {            
            e.printStackTrace();
        }        
        return new RetrieveResponse<ListProductsResponse >(false, "Erro ao montar lista", null);
    }
    
    // @Cacheable(value = "getAllProductListByUser")
    public RetrieveResponse<List<UserListsProductsResponse>> getAllProductListByUser(long idUser, Markets market){
        MarketService service = _marketServiceFactory.getService(market);
        List<ProductList> productLists =  _productListRepo.findByUser_Id(idUser);

        List<UserListsProductsResponse> listsResponse = new ArrayList<>();

        for (ProductList productList : productLists) {
            List<ProductItem> productsItems = productList.getProductsItems();            
            List<ProductDto> productDtos = service.getProductList(productsItems);

            double total = 0;
            for (int i = 0; i < productsItems.size(); i++) {
                int quantity = productsItems.get(i).getQuantidade();
                productDtos.get(i).setQuantidade(quantity);
                total = productDtos.get(i).getPreco() * quantity;
            }

            var respItem = UserListsProductsResponse.builder()
                .idList(productList.getIdLista())
                .name(productList.getName())
                .quantityOfProducts(productsItems.size())
                .total(total).build();

            listsResponse.add(respItem);            
        }

        return new RetrieveResponse<List<UserListsProductsResponse>>(true, "", Optional.of(listsResponse));
        // return new RetrieveResponse<Collection<UserListsProductsResponse>>(false, "Erro no processamento do JSON", null);
    }

    public RetrieveResponse<List<ListProductsResponse>> getProductListByFilter(GetFilterModel filter){
        // TODO implementar metodo no repo para usar o LIKE '%term%' para pesquisar listas

        return new RetrieveResponse<List<ListProductsResponse>>(true, "", Optional.of(null));
    }

    
    public List<ProductItem> getProductsItemsById(long idList) {
        var productList =_productListRepo.findById(idList);
        if(productList.isPresent()){
            return productList.get().getProductsItems();
    
        }
    
        return new ArrayList<ProductItem>();
        
    }


    private List<ProductItem> mapProductDtoToProductItem(List<ProductDto> productsDto, ProductList productList){
        
        List<ProductItem> productItems = new ArrayList<>();
        for (ProductDto productDto : productsDto) {
            var p = new ProductItem();

            p.setName(productDto.getNome());
            p.setPreco(productDto.getPreco());
            p.setQuantidade(productDto.getQuantidade());
            p.setUrlImg(productDto.getUrlImg());
            p.setLista(productList);                
            p.setGtin(productDto.getGtin());
            
            productItems.add(p);

        }

        return productItems;
    }
        
}

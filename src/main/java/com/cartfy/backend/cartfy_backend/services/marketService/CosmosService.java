package com.cartfy.backend.cartfy_backend.services.marketService;

import java.net.http.HttpClient;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.cartfy.backend.cartfy_backend.entities.ProductItem;
import com.cartfy.backend.cartfy_backend.models.markets.Markets;
import com.cartfy.backend.cartfy_backend.models.products.ProductCosmos;
import com.cartfy.backend.cartfy_backend.models.requests.ProductDto;
import com.cartfy.backend.cartfy_backend.services.annotations.MarketServiceType;
import com.fasterxml.jackson.databind.ObjectMapper;

import reactor.core.publisher.Mono;

@Component
@MarketServiceType(Markets.COSMOS)
public class CosmosService implements MarketService{

    
    @Value("${URL_PRODUCTS}")
    private String URL_PRODUCTS;
    @Value("${X_Cosmos_Token}")
    private String X_Cosmos_Token;

    WebClient webClient;
    HttpClient client;

    ObjectMapper mapper;

    public CosmosService(){
        webClient = WebClient.create(URL_PRODUCTS);
        client = HttpClient.newBuilder().build();

        mapper = new ObjectMapper();
    }

    @Override
    @Cacheable(value = "getProductListMarket", keyGenerator = "productItemKeyGenerator")
    public List<ProductDto> getProductList(List<ProductItem> productsItems) {                                              
        List<ProductDto> list = new ArrayList<>();

        for (ProductItem productItem : productsItems) {
            ProductDto product = getProductByGtin(productItem.getGtin());
            if(product == null){
                list.add(new ProductDto());
            }
            list.add(product);
        } 

        return list;
    }

    @Override
    public ProductDto getProductByGtin(String gtin) {
        try {
            webClient = WebClient.create(URL_PRODUCTS);
            Mono<ProductCosmos> response = webClient.get()
                .uri("/gtins/" + gtin).header("X-Cosmos-Token", X_Cosmos_Token)
                .retrieve()
                .bodyToMono(ProductCosmos.class);
    
            ProductCosmos dados = response.block();

            return mapToProductDto(dados);
        } catch (Exception e) {            
            e.printStackTrace();
        }

        return null;
    }        
    
    private ProductDto mapToProductDto(ProductCosmos productCosmos){
        return new ProductDto();        
    }

    @Override
    public List<ProductDto> getProductByTerm(String term) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getProductByTerm'");
    }

}

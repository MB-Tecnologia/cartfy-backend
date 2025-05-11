package com.cartfy.backend.cartfy_backend.services.marketService;

import java.net.http.HttpClient;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.cartfy.backend.cartfy_backend.models.markets.Markets;
import com.cartfy.backend.cartfy_backend.models.products.ListProductCosmos;
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
    public List<ProductDto> getProductList(String[] gtins) {                                              
        List<ProductDto> list = new ArrayList<>();

        for (String gtin : gtins) {
            ProductDto product = getProductByGtin(gtin);
            if(product == null){
                list.add(ProductDto.builder()
                    .name("")
                    .preco(0)
                    .quantidade(0)
                    .urlImg("")
                    .gtin(String.valueOf(gtin)).build());
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
        return ProductDto.builder()
                    .name(productCosmos.getDescription())
                    .preco(productCosmos.getAvg_price())
                    .quantidade(1)
                    .urlImg(productCosmos.getThumbnail())
                    .gtin(String.valueOf(productCosmos.getGtin())).build();        
    }

    private List<ProductDto> mapToProductsDto(ListProductCosmos productCosmos){
        List<ProductDto> productsResponse = new ArrayList<ProductDto>();
        for (ProductCosmos product : productCosmos.products()) {
            productsResponse.add(mapToProductDto(product));
        }
        return productsResponse;    
    }
}

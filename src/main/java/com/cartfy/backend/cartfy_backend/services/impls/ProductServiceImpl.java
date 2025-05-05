package com.cartfy.backend.cartfy_backend.services.impls;

import java.util.List;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import com.cartfy.backend.cartfy_backend.models.products.ListProductCosmos;
import com.cartfy.backend.cartfy_backend.models.products.ProductCosmos;
import com.cartfy.backend.cartfy_backend.models.products.ProductResponse;
import com.cartfy.backend.cartfy_backend.services.ProductService;

@Service
public class ProductServiceImpl implements ProductService {

    @Value("${URL_PRODUCTS}")
    private String URL_PRODUCTS;
    @Value("${X_Cosmos_Token}")
    private String X_Cosmos_Token;

    WebClient webClient;

    public ProductServiceImpl() {        
    }
    
    
    @Override    
    @Cacheable("productGtinCache")
    public ProductResponse getProductByGtin(long gtin) {
        try {
            webClient = WebClient.create(URL_PRODUCTS);
            Mono<ProductCosmos> response = webClient.get()
                .uri("/gtins/" + gtin).header("X-Cosmos-Token", X_Cosmos_Token)
                .retrieve()
                .bodyToMono(ProductCosmos.class);
    
            ProductCosmos dados = response.block();

            return mapToProductResponse(dados);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return null;
    }
    
    private ProductResponse mapToProductResponse(ProductCosmos productCosmos){
        return new ProductResponse(productCosmos.getDescription(), productCosmos.getGtin(), productCosmos.getAvg_price(), productCosmos.getThumbnail());
    }

    private List<ProductResponse> mapToProductResponse(ListProductCosmos productCosmos){
        List<ProductResponse> productsResponse = new ArrayList<ProductResponse>();
        for (ProductCosmos product : productCosmos.products()) {
            productsResponse.add(mapToProductResponse(product));
        }
        return productsResponse;
    }

    @Override
    @Cacheable("productTermCache")
    public List<ProductResponse> getProductByTerm(String term) {        
        try {
            webClient = WebClient.create(URL_PRODUCTS);
            Mono<ListProductCosmos> response = webClient.get()
                .uri("/products/?query=" + term).header("X-Cosmos-Token", X_Cosmos_Token)
                .retrieve()
                .bodyToMono(ListProductCosmos.class);
    
            ListProductCosmos dados = response.block();

            return mapToProductResponse(dados);
        } catch (Exception e) {            
            e.printStackTrace();
        }
    
        return null;
    }
}
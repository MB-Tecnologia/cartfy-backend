package com.cartfy.backend.cartfy_backend.services.impls;

import java.util.List;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import com.cartfy.backend.cartfy_backend.models.markets.Markets;
import com.cartfy.backend.cartfy_backend.models.products.ListProductCosmos;
import com.cartfy.backend.cartfy_backend.models.products.ProductCosmos;
import com.cartfy.backend.cartfy_backend.models.requests.ProductDto;
import com.cartfy.backend.cartfy_backend.services.ProductService;
import com.cartfy.backend.cartfy_backend.services.marketService.MarketService;
import com.cartfy.backend.cartfy_backend.services.marketService.MarketServiceFactory;

@Service
public class ProductServiceImpl implements ProductService {

    @Value("${URL_PRODUCTS}")
    private String URL_PRODUCTS;
    @Value("${X_Cosmos_Token}")
    private String X_Cosmos_Token;
    
    @Autowired
    private MarketServiceFactory marketServiceFactory;

    WebClient webClient;

    public ProductServiceImpl() {        
    }
    
    
    @Override    
    @Cacheable(value = "productGtinCache")
    public ProductDto getProductByGtin(long gtin, Markets market) {
        try {
            MarketService service = marketServiceFactory.getService(market);
    
            return service.getProductByGtin(String.valueOf(gtin));            
        } catch (Exception e) {            
            e.printStackTrace();
        }

        return null;
    }
    
    private ProductDto mapToProductResponse(ProductCosmos productCosmos){
        return ProductDto.builder()
            .name(productCosmos.getDescription())
            .preco(productCosmos.getAvg_price())
            .quantidade(1) // TODO: Add qtd
            .urlImg(productCosmos.getThumbnail())
            .gtin(String.valueOf(productCosmos.getGtin())).build();
    }

    private List<ProductDto> mapToProductResponse(ListProductCosmos productCosmos){
        List<ProductDto> productsResponse = new ArrayList<ProductDto>();
        for (ProductCosmos product : productCosmos.products()) {
            productsResponse.add(mapToProductResponse(product));
        }
        return productsResponse;
    }

    @Override
    @Cacheable("productTermCache")
    public List<ProductDto> getProductByTerm(String term, Markets market) {        
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
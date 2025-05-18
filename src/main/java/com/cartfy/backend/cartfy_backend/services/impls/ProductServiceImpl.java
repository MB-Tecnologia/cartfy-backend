package com.cartfy.backend.cartfy_backend.services.impls;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.stereotype.Service;

import com.cartfy.backend.cartfy_backend.models.markets.Markets;
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

    @Override
    @Cacheable("productTermCache")
    public List<ProductDto> getProductByTerm(String term, Markets market) {        
        try {
            MarketService service = marketServiceFactory.getService(market);

            return service.getProductByTerm(term);
            
        } catch (Exception e) {            
            e.printStackTrace();
        }
    
        return null;
    }

}
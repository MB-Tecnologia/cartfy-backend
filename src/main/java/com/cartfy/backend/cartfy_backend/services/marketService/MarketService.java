package com.cartfy.backend.cartfy_backend.services.marketService;

import java.util.List;

import com.cartfy.backend.cartfy_backend.entities.ProductItem;
import com.cartfy.backend.cartfy_backend.models.requests.ProductDto;

public interface MarketService {
    public List<ProductDto> getProductList(List<ProductItem> gtins);
    public ProductDto getProductByGtin(String gtin);
    public List<ProductDto> getProductByTerm(String term);    
}

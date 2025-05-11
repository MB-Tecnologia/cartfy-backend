package com.cartfy.backend.cartfy_backend.services;

import java.util.List;

import com.cartfy.backend.cartfy_backend.models.markets.Markets;
import com.cartfy.backend.cartfy_backend.models.requests.ProductDto;

public interface ProductService {

    ProductDto getProductByGtin(long gtin, Markets market);
    List<ProductDto> getProductByTerm(String term, Markets market);
}

package com.cartfy.backend.cartfy_backend.services;

import java.util.List;

import com.cartfy.backend.cartfy_backend.models.products.ProductResponse;

public interface ProductService {

    ProductResponse getProductByGtin(Long gtin);
    List<ProductResponse> getProductByTerm(String term);
}

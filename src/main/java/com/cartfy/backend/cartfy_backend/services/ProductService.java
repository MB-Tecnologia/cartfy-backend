package com.cartfy.backend.cartfy_backend.services;

import java.util.List;

import com.cartfy.backend.cartfy_backend.models.products.ProductResponse;

public interface ProductService {

    ProductResponse getProductByGtin(long gtin);
    List<ProductResponse> getProductByTerm(String term);
}

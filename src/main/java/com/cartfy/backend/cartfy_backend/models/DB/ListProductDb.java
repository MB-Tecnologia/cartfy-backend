package com.cartfy.backend.cartfy_backend.models.DB;

import com.cartfy.backend.cartfy_backend.models.products.ProductResponse;
import java.util.List;

public record ListProductDb(
    String name,    
    List<ProductResponse> products
) {
} 

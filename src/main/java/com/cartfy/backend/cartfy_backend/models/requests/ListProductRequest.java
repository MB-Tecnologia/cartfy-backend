package com.cartfy.backend.cartfy_backend.models.requests;

import java.util.List;

import com.cartfy.backend.cartfy_backend.entities.User;
import com.cartfy.backend.cartfy_backend.models.products.ProductResponse;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize
public record ListProductRequest(
        String name,
        User user,
        List<ProductResponse> products
    )
{ }

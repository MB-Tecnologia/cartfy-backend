package com.cartfy.backend.cartfy_backend.models.requests;

import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize
public record ListProductRequest(
        String name,
        long idUser,
        List<ProductDto> products
    )
{ }

package com.cartfy.backend.cartfy_backend.models.products;

public record  ProductResponse(
        String nome,
        long gtin,
        double preco,
        String urlImg        
        )
{ } 
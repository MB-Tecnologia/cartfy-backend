package com.cartfy.backend.cartfy_backend.models.markets;

public record ExtractorRequest(
    String[] gtins,
    int market,
    String cep
) {
    
}

package com.cartfy.backend.cartfy_backend.models.markets;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize
public record ExtractorProductResponse(
    String nome,    
    long gtin,
    double preco,
    String cep,
    String urlImg
) {    
}

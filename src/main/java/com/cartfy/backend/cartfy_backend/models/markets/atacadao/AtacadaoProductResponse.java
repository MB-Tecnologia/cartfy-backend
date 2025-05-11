package com.cartfy.backend.cartfy_backend.models.markets.atacadao;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize
public record AtacadaoProductResponse(
    String nome,    
    long gtin,
    double preco,
    String cep,
    String urlImg
) {    
}

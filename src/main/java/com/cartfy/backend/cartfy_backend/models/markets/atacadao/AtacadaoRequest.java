package com.cartfy.backend.cartfy_backend.models.markets.atacadao;

public record AtacadaoRequest(
    String[] gtins,
    int market,
    String cep
) {
    
}

package com.cartfy.backend.cartfy_backend.models.markets.atacadao;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize
public record AtacadaoResponse(
    AtacadaoProductResponse[] productResponses
) {    
}

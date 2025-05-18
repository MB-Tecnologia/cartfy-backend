package com.cartfy.backend.cartfy_backend.models.markets.atacadao;

import com.cartfy.backend.cartfy_backend.models.requests.ProductDto;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize
public record AtacadaoResponse(
    ProductDto[] productResponses
) {    
}

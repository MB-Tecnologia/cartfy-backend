package com.cartfy.backend.cartfy_backend.models.responses;

import java.util.List;

import com.cartfy.backend.cartfy_backend.models.markets.Markets;
import com.cartfy.backend.cartfy_backend.models.requests.ProductDto;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize
public record ComparePriceListResponse(
        Markets market,
        List<ProductDto> listItems,
        boolean sucess,
        String msg       
    )
{ }

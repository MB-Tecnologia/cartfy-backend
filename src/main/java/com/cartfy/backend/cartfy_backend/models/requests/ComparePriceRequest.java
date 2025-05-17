package com.cartfy.backend.cartfy_backend.models.requests;

import com.cartfy.backend.cartfy_backend.models.markets.Markets;


public record ComparePriceRequest(
    long idList,
    Markets[] markets
) {

}

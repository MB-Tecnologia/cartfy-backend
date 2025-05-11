package com.cartfy.backend.cartfy_backend.models.responses;

import java.util.List;

import com.cartfy.backend.cartfy_backend.models.requests.ProductDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.Builder;

@JsonSerialize
@Builder
public class ListProductsResponse {
    @JsonProperty("name")
    String name;

    @JsonProperty("products")
    List<ProductDto> products;
}

package com.cartfy.backend.cartfy_backend.models.responses;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;


import lombok.Builder;

@JsonSerialize
@Builder
public class UserListsProductsResponse {
    @JsonProperty("name")
    String name;

    @JsonProperty("quantityOfProducts")
    int quantityOfProducts;

    @JsonProperty("total")
    double total;
}

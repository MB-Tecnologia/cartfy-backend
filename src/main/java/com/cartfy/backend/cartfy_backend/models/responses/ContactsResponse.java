package com.cartfy.backend.cartfy_backend.models.responses;

import java.util.List;
import com.cartfy.backend.cartfy_backend.models.products.ProductResponse;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize
public class ContactsResponse {
    @JsonProperty("name")
    String name;

    @JsonProperty("products")
    List<ProductResponse> products;
}

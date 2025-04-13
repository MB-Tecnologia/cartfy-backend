package com.cartfy.backend.cartfy_backend.models.listProducts;

import java.util.List;
import com.cartfy.backend.cartfy_backend.models.products.ProductResponse;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize
public class ListProductsResponse {
    @JsonProperty("name")
    String name;

    @JsonProperty("products")
    List<ProductResponse> products;
}

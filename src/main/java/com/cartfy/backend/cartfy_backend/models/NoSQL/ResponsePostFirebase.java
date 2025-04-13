package com.cartfy.backend.cartfy_backend.models.NoSQL;


import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize
public record ResponsePostFirebase(
    String name
) {
    
}

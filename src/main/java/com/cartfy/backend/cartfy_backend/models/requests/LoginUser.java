package com.cartfy.backend.cartfy_backend.models.requests;

public record LoginUser(
        String email,
        String senha        
) {
}
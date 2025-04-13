package com.cartfy.backend.cartfy_backend.models.requests;

import java.util.Optional;

public record GetFilterModel(
    Optional<String> listName 
) {
} 
package com.cartfy.backend.cartfy_backend.models.requests;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize
public record ContactCreateRequest(
        String nickname,
        long idUser,
        long idContactUser                
    )
{ }

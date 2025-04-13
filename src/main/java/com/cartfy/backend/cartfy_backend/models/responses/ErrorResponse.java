package com.cartfy.backend.cartfy_backend.models.responses;

public record ErrorResponse(
    String msg
) implements BaseResponse{
    
}

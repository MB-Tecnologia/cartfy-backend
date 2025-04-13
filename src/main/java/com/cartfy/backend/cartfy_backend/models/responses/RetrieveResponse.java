package com.cartfy.backend.cartfy_backend.models.responses;

import java.util.Optional;

public record RetrieveResponse<T>(
    boolean sucess,
    String msg,
    Optional<T> result
) implements BaseResponse{
    
}

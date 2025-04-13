package com.cartfy.backend.cartfy_backend.models.responses;


public record OperationResponse(
        boolean sucess,
        String msg    
)  implements BaseResponse {
    
}

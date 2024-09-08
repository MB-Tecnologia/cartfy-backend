package com.cartfy.backend.cartfy_backend.utils.exceptions;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}

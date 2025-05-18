package com.cartfy.backend.cartfy_backend.models.markets;

public enum Markets {
    COSMOS(1),
    // ATACADAO(2),
    EXTRA(2),
    PAODEACUCAR(3);

    private int value;
    Markets(int i) {
        this.value = i;
    }   

    public int getValue() {
        return value;
    }
}

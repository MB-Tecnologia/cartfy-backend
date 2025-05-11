package com.cartfy.backend.cartfy_backend.models.requests;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductDto {

    private String name;

    private double preco;
    
    private int quantidade;

    private String urlImg;

    private String gtin;
}

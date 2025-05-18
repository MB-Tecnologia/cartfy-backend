package com.cartfy.backend.cartfy_backend.models.requests;


import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@JsonSerialize
@NoArgsConstructor
public class ProductDto {

    private String nome;
    private double preco;
    private int quantidade;
    private String urlImg;
    private String gtin;
}

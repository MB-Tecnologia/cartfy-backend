package com.cartfy.backend.cartfy_backend.models.requests;

public record CreateUser(    
    String cpfCnpj,
    String email,
    String senha,   
    int telefone,
    int cep,
    int numeroEndereco
) {
}



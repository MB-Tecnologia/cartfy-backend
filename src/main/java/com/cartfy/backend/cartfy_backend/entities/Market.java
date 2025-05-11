package com.cartfy.backend.cartfy_backend.entities;

import jakarta.persistence.GeneratedValue;


import com.google.auto.value.AutoValue.Builder;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "Mercados")
@Table(name = "Mercados")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Market {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long idMercado;

    @Column(name = "nome")
    private String name;

    @Column(name = "razaoSocial")
    private String corporateName;

    @Column(name = "endereco")
    private String address;

    @Column(name = "cnpj", length = 14)
    private String cnpj;

}
package com.cartfy.backend.cartfy_backend.entities;


import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "lista_de_compras")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ProductList {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private long idLista;
    
    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name="usuario_id", nullable = false)    
    private User user;
    
    @Column(name="nome")
    private String name;

    @OneToMany(mappedBy = "lista", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<ProductItem> productsItems = new ArrayList<>();
    
}
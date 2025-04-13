package com.cartfy.backend.cartfy_backend.entities;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
    @Column(name = "id_lista")
    private long idLista;
    
    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name="id_usuario", nullable = false)    
    private User usuario;
    
    @Column(name="nome")
    private String name;

    @Column(name = "url_lista")
	private String urlLista;

    @Column(name = "dt_inclusao")
    private String dtIncluso;

    @Column(name = "dt_alteracao")
    private String dtAlteracao;
}

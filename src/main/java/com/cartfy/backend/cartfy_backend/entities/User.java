package com.cartfy.backend.cartfy_backend.entities;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Entity
@Table(name = "usuario")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    @Column(unique = true, name = "cpf_cnpj", nullable = false)
    private String cpfCnpj;
    
    @Column(unique = true)
    private String email;

    private String senha;

    @Column(nullable = true)
    private int telefone;
    
    private int cep;

    @Column(name = "numero_endereco", nullable = true)
    private int numeroEndereco;

    @Column(name = "dt_inclusao")
    private LocalDateTime  dtInclusao;

}

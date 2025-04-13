package com.cartfy.backend.cartfy_backend.entities;

import java.time.LocalDateTime;

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
@Entity(name = "Contatos")
@Table(name = "contatos")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Contact {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id_contato;

    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name="id_usuario", nullable = false)        
    private User usuario;

    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name="id_contato_salvo", nullable = false)    
    private User usuarioContato;


    @Column(name = "apelido", nullable = false)
    private String apelido;
    
    @Column(name = "dt_inclusao")
    private LocalDateTime  dtInclusao;

    @Column(name = "dt_alteracao")
    private LocalDateTime  dtAlteracao;
}

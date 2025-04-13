package com.cartfy.backend.cartfy_backend.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cartfy.backend.cartfy_backend.entities.Contact;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {   
    // @Query("SELECT c FROM Contatos c WHERE c.id_usuario = ?1") 
    // Collection<Contact> findByUsuario(long idUser);

    Collection<Contact> findByUsuario_Id(long idUser);
}

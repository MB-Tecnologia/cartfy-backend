package com.cartfy.backend.cartfy_backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cartfy.backend.cartfy_backend.entities.ProductList;

@Repository
public interface ProductListRepository extends JpaRepository<ProductList, Long> {    
    List<ProductList> findByName(String name);
    List<ProductList> findByNameContaining(String term);
    List<ProductList> findByUser_Id(long idUser);
}

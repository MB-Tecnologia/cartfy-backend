package com.cartfy.backend.cartfy_backend.repository;

import com.cartfy.backend.cartfy_backend.entities.ProductItem;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;;

@Repository
public interface ProductRepository extends JpaRepository<ProductItem, Long>{
    
}

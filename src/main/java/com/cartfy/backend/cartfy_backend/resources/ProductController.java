package com.cartfy.backend.cartfy_backend.resources;

import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cartfy.backend.cartfy_backend.models.products.ProductResponse;
import com.cartfy.backend.cartfy_backend.services.ProductService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping("/api/produto")
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping("/{gtin}")
    public ResponseEntity<ProductResponse> getProductByGtin(@PathVariable Long gtin) {
        var product = productService.getProductByGtin(gtin);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }
    
    @GetMapping("/descricao/{termo}")
    public ResponseEntity<List<ProductResponse>> getProductByTerm(@PathVariable String termo) {
        var products = productService.getProductByTerm(termo);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }
    
}

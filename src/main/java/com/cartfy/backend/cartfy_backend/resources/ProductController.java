package com.cartfy.backend.cartfy_backend.resources;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cartfy.backend.cartfy_backend.models.products.ProductResponse;
import com.cartfy.backend.cartfy_backend.models.responses.RetrieveResponse;
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
    public ResponseEntity<RetrieveResponse<ProductResponse>> getProductByGtin(@PathVariable long gtin) {
        var product = productService.getProductByGtin(gtin);
        return new ResponseEntity<RetrieveResponse<ProductResponse>>(new RetrieveResponse<ProductResponse>(true, "", Optional.of(product)), HttpStatus.OK);
    }
    
    @GetMapping("/descricao/{termo}")
    public ResponseEntity<RetrieveResponse<List<ProductResponse>>> getProductByTerm(@PathVariable String termo) {
        var products = productService.getProductByTerm(termo);
        
        return new ResponseEntity<RetrieveResponse<List<ProductResponse>>>(new RetrieveResponse<List<ProductResponse>>(true, "", Optional.of(products)), HttpStatus.OK);
    }
    
}

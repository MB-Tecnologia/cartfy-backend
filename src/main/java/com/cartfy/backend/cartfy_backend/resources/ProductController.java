package com.cartfy.backend.cartfy_backend.resources;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cartfy.backend.cartfy_backend.models.markets.Markets;
import com.cartfy.backend.cartfy_backend.models.requests.ProductDto;
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
    public ResponseEntity<RetrieveResponse<ProductDto>> getProductByGtin(@PathVariable long gtin, Markets market) {
        var product = productService.getProductByGtin(gtin, market);        
        return new ResponseEntity<RetrieveResponse<ProductDto>>(new RetrieveResponse<ProductDto>(true, "", Optional.of(product)), HttpStatus.OK);
    }
    
    @GetMapping("/descricao/{termo}")
    public ResponseEntity<RetrieveResponse<List<ProductDto>>> getProductByTerm(@PathVariable String termo, Markets market) {
        var products = productService.getProductByTerm(termo, market);
        
        return new ResponseEntity<RetrieveResponse<List<ProductDto>>>(new RetrieveResponse<List<ProductDto>>(true, "", Optional.of(products)), HttpStatus.OK);
    }
    
}

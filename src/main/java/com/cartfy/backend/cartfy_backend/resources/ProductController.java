package com.cartfy.backend.cartfy_backend.resources;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping("api/produto")
public class ProductController {
    
    @GetMapping("{gtin}")
    public String getProductByGtin(@PathVariable String gtin) {
        return new String();
    }
    
    @GetMapping("/descricao/{termo}")
    public String getProductByTerm(@PathVariable String termo) {
        return new String();
    }
    
}

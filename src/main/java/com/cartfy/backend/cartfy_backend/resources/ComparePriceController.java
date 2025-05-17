package com.cartfy.backend.cartfy_backend.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cartfy.backend.cartfy_backend.models.requests.ComparePriceRequest;
import com.cartfy.backend.cartfy_backend.services.ComparePriceService;


import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/comparar_precos")
public class ComparePriceController {    

    @Autowired
    private ComparePriceService comparePriceService;


    @PostMapping("")
    public ResponseEntity<Object> comparar_listas(@RequestBody ComparePriceRequest listProductRequest) {
        try {
            var response = comparePriceService.comparePricesList(listProductRequest);
    
            if(response.sucess()){
                return ResponseEntity.ok().body(response);
            }
            
            return ResponseEntity.status(HttpStatusCode.valueOf(500)).body(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatusCode.valueOf(500)).body("null");
        }
        
    }

}

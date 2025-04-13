package com.cartfy.backend.cartfy_backend.resources;

import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cartfy.backend.cartfy_backend.models.requests.GetFilterModel;
import com.cartfy.backend.cartfy_backend.models.requests.ListProductRequest;
import com.cartfy.backend.cartfy_backend.models.responses.ListProductsResponse;
import com.cartfy.backend.cartfy_backend.models.responses.OperationResponse;
import com.cartfy.backend.cartfy_backend.models.responses.RetrieveResponse;
import com.cartfy.backend.cartfy_backend.services.ProductListService;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("/api/lista_produto")
public class ListProductController {
    @Autowired
    private ProductListService productListService;


    @GetMapping("/{idList}")
    public ResponseEntity<RetrieveResponse<ListProductsResponse>> getByIdList(@PathVariable long idList) {
        try{
            var response = productListService.getProductList(idList);
            if(response.sucess()){
                return ResponseEntity.ok().body(response);
            } else if(response.result() == null){
                return ResponseEntity.status(HttpStatusCode.valueOf(400)).body(response);
            }
            
            return ResponseEntity.status(HttpStatusCode.valueOf(500)).body(response);
        } catch (Exception e){
            System.out.println("--------------------------------------------");
            e.printStackTrace();
            return ResponseEntity.status(HttpStatusCode.valueOf(500)).body(new RetrieveResponse<ListProductsResponse>(false, e.toString(), null));
        }
    }

    @GetMapping("/usuario/{idUser}")
    public ResponseEntity<RetrieveResponse<Collection<ListProductsResponse>>> getAllByIdUser(@PathVariable long idUser) {
        try{
            var response = productListService.getAllProductListByUser(idUser);
            if(response.sucess()){
                return ResponseEntity.ok().body(response);
            } else if(response.result() == null){
                return ResponseEntity.status(HttpStatusCode.valueOf(400)).body(response);
            }
            
            return ResponseEntity.status(HttpStatusCode.valueOf(500)).body(response);
        } catch (Exception e){
            System.out.println("--------------------------------------------");
            e.printStackTrace();
            return ResponseEntity.status(HttpStatusCode.valueOf(500)).body(new RetrieveResponse<Collection<ListProductsResponse>>(false, e.toString(), null));
        }
    }


    @GetMapping("")
    public ResponseEntity<RetrieveResponse<Collection<ListProductsResponse>>> getByFilter(Optional<String> listName) {
        try{
            var filter = new GetFilterModel(listName);
            var response = productListService.getProductListByFilter(filter);
            if(response.sucess()){
                return ResponseEntity.ok().body(response);
            }
            
            return ResponseEntity.status(HttpStatusCode.valueOf(500)).body(response);
        } catch (Exception e){
            System.out.println("--------------------------------------------");
            e.printStackTrace();
            return ResponseEntity.status(HttpStatusCode.valueOf(500)).body(new RetrieveResponse<Collection<ListProductsResponse>>(false, e.toString(), null));
        }
    }
    
    @PostMapping("")
    public ResponseEntity<OperationResponse> add(@RequestBody ListProductRequest listProductRequest) {
        var response = productListService.save(listProductRequest);

        if(response.sucess()){
            return ResponseEntity.ok().body(response);
        }
        
        return ResponseEntity.status(HttpStatusCode.valueOf(500)).body(response);
    }

    @PutMapping("/{idList}")
    public ResponseEntity<OperationResponse> update(@PathVariable long idList, @RequestBody ListProductRequest listProductRequest) {
        var response = productListService.update(idList, listProductRequest);
// TODO: Testar endpoints
        if(response.sucess()){
            return ResponseEntity.ok().body(response);
        }
        
        return ResponseEntity.status(HttpStatusCode.valueOf(500)).body(response);
    }
    
}

package com.cartfy.backend.cartfy_backend.resources;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cartfy.backend.cartfy_backend.models.markets.Markets;
import com.cartfy.backend.cartfy_backend.models.requests.GetFilterModel;
import com.cartfy.backend.cartfy_backend.models.requests.ListProductRequest;
import com.cartfy.backend.cartfy_backend.models.responses.ListProductsResponse;
import com.cartfy.backend.cartfy_backend.models.responses.OperationResponse;
import com.cartfy.backend.cartfy_backend.models.responses.RetrieveResponse;
import com.cartfy.backend.cartfy_backend.models.responses.UserListsProductsResponse;
import com.cartfy.backend.cartfy_backend.services.ProductListService;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("/api/lista_produto")
public class ListProductController {
    @Autowired
    private ProductListService productListService;


    @GetMapping("/{idList}")
    public ResponseEntity<RetrieveResponse<ListProductsResponse>> getByIdList(@PathVariable long idList, @RequestParam Markets market) {
        try{
            var response = productListService.getProductList(idList, market);
            if(response.sucess()){
                return ResponseEntity.ok().body(response);
            } else if(response.result() == null){
                return ResponseEntity.status(HttpStatusCode.valueOf(400)).body(response);
            }
            
            return ResponseEntity.status(HttpStatusCode.valueOf(500)).body(response);
        } catch (Exception e){
            System.out.println("--------------------------------------------");
            e.printStackTrace();
            return ResponseEntity.status(HttpStatusCode.valueOf(500)).body(new RetrieveResponse<ListProductsResponse >(false, e.toString(), null));
        }
    }

    @GetMapping("/usuario/{idUser}")
    public ResponseEntity<RetrieveResponse<List<UserListsProductsResponse>>> getAllByIdUser(@PathVariable long idUser, @RequestParam Markets market) {
        try{
            var response = productListService.getAllProductListByUser(idUser, market);
            if(response.sucess()){
                return ResponseEntity.ok().body(response);
            } else if(response.result() == null){
                return ResponseEntity.status(HttpStatusCode.valueOf(400)).body(response);
            }
            
            return ResponseEntity.status(HttpStatusCode.valueOf(500)).body(response);
        } catch (Exception e){
            System.out.println("--------------------------------------------");
            e.printStackTrace();
            return ResponseEntity.status(HttpStatusCode.valueOf(500)).body(new RetrieveResponse<List<UserListsProductsResponse>>(false, e.toString(), null));
        }
    }


    @GetMapping("")
    public ResponseEntity<RetrieveResponse<List<ListProductsResponse>>> getByFilter(Optional<String> listName) {
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
            return ResponseEntity.status(HttpStatusCode.valueOf(500)).body(new RetrieveResponse<List<ListProductsResponse>>(false, e.toString(), null));
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

        if(response.sucess()){
            return ResponseEntity.ok().body(response);
        }
        
        return ResponseEntity.status(HttpStatusCode.valueOf(500)).body(response);
    }
    
    @DeleteMapping("/{idList}")
    public ResponseEntity<OperationResponse> delete(@PathVariable long idList){
        var response = productListService.delete(idList);
        if(response.sucess()){
            return ResponseEntity.ok().body(response);
        }
        
        return ResponseEntity.status(HttpStatusCode.valueOf(500)).body(response);
    }
}

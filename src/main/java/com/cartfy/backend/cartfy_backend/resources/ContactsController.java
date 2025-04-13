package com.cartfy.backend.cartfy_backend.resources;

import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cartfy.backend.cartfy_backend.entities.Contact;
import com.cartfy.backend.cartfy_backend.models.requests.ContactCreateRequest;
import com.cartfy.backend.cartfy_backend.models.requests.ContactUpdateRequest;
import com.cartfy.backend.cartfy_backend.models.responses.OperationResponse;
import com.cartfy.backend.cartfy_backend.models.responses.RetrieveResponse;
import com.cartfy.backend.cartfy_backend.services.ContactService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("/api/contatos")
public class ContactsController {
    @Autowired
    private ContactService contactService;


    @GetMapping("/{idUser}")
    public ResponseEntity<RetrieveResponse<Collection<Contact>>> getByidUser(@PathVariable long idUser) {
        try{
            var response = contactService.getAllContactsByUser(idUser);
            if(response.sucess()){
                return ResponseEntity.ok().body(response);
            } else if(response.result() == null){
                return ResponseEntity.status(HttpStatusCode.valueOf(400)).body(response);
            }
            
            return ResponseEntity.status(HttpStatusCode.valueOf(500)).body(response);
        } catch (Exception e){
            System.out.println("--------------------------------------------");
            e.printStackTrace();
            return ResponseEntity.status(HttpStatusCode.valueOf(500)).body(new RetrieveResponse<Collection<Contact>>(false, e.toString(), null));
        }
    }

    @PostMapping("")
    public ResponseEntity<OperationResponse> add(@RequestBody ContactCreateRequest contactRequest) {
        var response = contactService.save(contactRequest);

        if(response.sucess()){
            return ResponseEntity.ok().body(response);
        }
        
        return ResponseEntity.status(HttpStatusCode.valueOf(500)).body(response);
    }

    @PutMapping("/{idContact}")
    public ResponseEntity<OperationResponse> update(@PathVariable long idContact, @RequestBody ContactUpdateRequest contactUpdateRequest) {
        var response = contactService.update(idContact, contactUpdateRequest);
        if(response.sucess()){
            return ResponseEntity.ok().body(response);
        }
        
        return ResponseEntity.status(HttpStatusCode.valueOf(500)).body(response);
    }
    
    @DeleteMapping("/{idContact}")
    public ResponseEntity<OperationResponse> delete(@PathVariable long idContact){
        var response = contactService.delete(idContact);
        if(response.sucess()){
            return ResponseEntity.ok().body(response);
        }
        
        return ResponseEntity.status(HttpStatusCode.valueOf(500)).body(response);
    }
}

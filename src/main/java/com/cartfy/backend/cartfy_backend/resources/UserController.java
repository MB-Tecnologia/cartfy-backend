package com.cartfy.backend.cartfy_backend.resources;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cartfy.backend.cartfy_backend.entities.User;
import com.cartfy.backend.cartfy_backend.models.requests.CreateUser;
import com.cartfy.backend.cartfy_backend.models.requests.LoginUser;
import com.cartfy.backend.cartfy_backend.models.requests.RecoveryJwtToken;
import com.cartfy.backend.cartfy_backend.models.responses.OperationResponse;
import com.cartfy.backend.cartfy_backend.models.responses.RetrieveResponse;
import com.cartfy.backend.cartfy_backend.services.UserService;


@RestController
@RequestMapping("/api")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<RetrieveResponse<RecoveryJwtToken>> authenticateUser(@RequestBody LoginUser loginUser) {
        try {
            RecoveryJwtToken token = userService.authenticateUser(loginUser);
            
            return ResponseEntity.ok().body((new RetrieveResponse<RecoveryJwtToken>(true, "", Optional.of(token))));        
        } catch(BadCredentialsException e){
            return ResponseEntity.badRequest().body(new RetrieveResponse<>(false, "Login Incorreto", null));
        }
        catch (Exception e) {            
            return ResponseEntity.internalServerError().body(new RetrieveResponse<>(false, "Falha no login", null));
        }
    }

    @PostMapping("/cadastro")
    public ResponseEntity<OperationResponse> createUser(@RequestBody CreateUser createUser) {
        try {
            OperationResponse resp = userService.createUser(createUser);

            if(resp.sucess()){
                return ResponseEntity.ok().body(resp);
            }

            return ResponseEntity.badRequest().body(resp);

        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(new OperationResponse(false, "Falha na criação de usuario"));
        }
    }

    @GetMapping("/usuario")
    public ResponseEntity<RetrieveResponse<User>> getByEmail(String email){
        try {
            RetrieveResponse<User> resp = userService.getByEmail(email);
            if(resp.sucess()){
                return ResponseEntity.ok().body(resp);
            }
            return ResponseEntity.badRequest().body(resp);
            
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(new RetrieveResponse<User>(false, e.toString(), null));
        }
    }
}

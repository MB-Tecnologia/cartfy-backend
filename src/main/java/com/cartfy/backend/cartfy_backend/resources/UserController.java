package com.cartfy.backend.cartfy_backend.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cartfy.backend.cartfy_backend.models.requests.CreateUser;
import com.cartfy.backend.cartfy_backend.models.requests.LoginUser;
import com.cartfy.backend.cartfy_backend.models.requests.RecoveryJwtToken;
import com.cartfy.backend.cartfy_backend.services.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping("/api")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public String getMethodName() {
        return "OK";
    }
    @GetMapping("/users/{teste}")
    public String teste(@PathVariable int teste) {
        return "OK " + teste;
    }
    @GetMapping("/users2")
    public String getMethodName2() {
        return "OK";
    }

    @PostMapping("/login")
    public ResponseEntity<RecoveryJwtToken> authenticateUser(@RequestBody LoginUser loginUser) {
        RecoveryJwtToken token = userService.authenticateUser(loginUser);
        return new ResponseEntity<>(token, HttpStatus.OK);
    }

    @PostMapping("/cadastro")
    public ResponseEntity<Void> createUser(@RequestBody CreateUser createUser) {
        userService.createUser(createUser);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}

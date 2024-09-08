package com.cartfy.backend.cartfy_backend.utils;
 
import java.util.Collections;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.cartfy.backend.cartfy_backend.entities.User;
public class UserDetailsImpl implements UserDetails{

    private User user; // Classe de usuário que criamos anteriormente

    public UserDetailsImpl(User user) {
        this.user = user;
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {    
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));        
    }

    @Override
    public String getPassword() {
        return user.getSenha();
    }
    
    @Override
    public String getUsername() {
        return user.getEmail();        
    }


    
}

package com.cartfy.backend.cartfy_backend.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.cartfy.backend.cartfy_backend.entities.User;
import com.cartfy.backend.cartfy_backend.repository.UserRepository;
import com.cartfy.backend.cartfy_backend.utils.exceptions.UserNotFoundException;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
        
    @Autowired
    private UserRepository userRepository;
    
    @Override
    public UserDetails loadUserByUsername(String username){        
        User user = userRepository.findByEmail(username).orElseThrow(() -> new UserNotFoundException("Usuario Não Encontrado"));
        return new UserDetailsImpl(user);
    }
    
}

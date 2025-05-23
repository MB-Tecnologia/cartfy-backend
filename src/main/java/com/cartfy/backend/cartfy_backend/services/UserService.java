package com.cartfy.backend.cartfy_backend.services;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.cartfy.backend.cartfy_backend.config.SecurityConfiguration;
import com.cartfy.backend.cartfy_backend.entities.User;
import com.cartfy.backend.cartfy_backend.models.requests.CreateUser;
import com.cartfy.backend.cartfy_backend.models.requests.LoginUser;
import com.cartfy.backend.cartfy_backend.models.requests.RecoveryJwtToken;
import com.cartfy.backend.cartfy_backend.models.responses.OperationResponse;
import com.cartfy.backend.cartfy_backend.models.responses.RetrieveResponse;
import com.cartfy.backend.cartfy_backend.repository.UserRepository;
import com.cartfy.backend.cartfy_backend.utils.JwtTokenService;
import com.cartfy.backend.cartfy_backend.utils.UserDetailsImpl;

@Service
public class UserService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenService jwtTokenService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SecurityConfiguration securityConfiguration;

    // Método responsável por autenticar um usuário e retornar um token JWT
    public RecoveryJwtToken authenticateUser(LoginUser loginUser) {
        // Cria um objeto de autenticação com o email e a senha do usuário
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(loginUser.email(), loginUser.senha());

        // Autentica o usuário com as credenciais fornecidas
        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        // Obtém o objeto UserDetails do usuário autenticado
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        // Gera um token JWT para o usuário autenticado
        return new RecoveryJwtToken(jwtTokenService.generateToken(userDetails));
    }

    // Método responsável por criar um usuário
    public OperationResponse createUser(CreateUser createUser) {

        Optional<User> user = userRepository.findByEmail(createUser.email());

        if(user.isPresent()){
            return new OperationResponse(false, "Email ja cadastrado");
        }

        // Cria um novo usuário com os dados fornecidos
        User newUser = User.builder()
                .email(createUser.email())
                .cpfCnpj(createUser.cpfCnpj())
                // Codifica a senha do usuário com o algoritmo bcrypt
                .senha(securityConfiguration.passwordEncoder().encode(createUser.senha()))
                .telefone(createUser.telefone())
                .cep(createUser.cep())
                .numeroEndereco(createUser.numeroEndereco())
                .dtInclusao(LocalDateTime.now())                      
                .build();

        // Salva o novo usuário no banco de dados
        userRepository.save(newUser);

        return new OperationResponse(true, "Usuario cadastrado");
                
    }

    public RetrieveResponse<User> getByEmail(String email){
        Optional<User> user = userRepository.findByEmail(email);
        if(user.isPresent()){
            return new RetrieveResponse<User>(true, email, user);
        }

        return new RetrieveResponse<User>(false, "Usuario nao encontrado", Optional.empty());

    }
}
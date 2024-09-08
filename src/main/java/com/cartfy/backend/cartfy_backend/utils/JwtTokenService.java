package com.cartfy.backend.cartfy_backend.utils;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;

@Service
public class JwtTokenService {
    @Value("${SECRET_KEY}")
    private String SECRET_KEY;

    private static final String ISSUER = "emissor do token";

    public String generateToken(UserDetailsImpl userDetailsImpl) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);

            return JWT.create()
                    .withIssuer(ISSUER) // Define o emissor do token
                    .withIssuedAt(creationDate()) // Define a data de emissão do token
                    .withExpiresAt(expirationDate()) // Define a data de expiração do token
                    .withSubject(userDetailsImpl.getUsername()) // Define o assunto do token (neste caso, o nome de
                                                                // usuário)
                    .sign(algorithm); // Assina o token usando o algoritmo especificado
        } catch (JWTCreationException exception) {
            throw new JWTCreationException("Erro ao gerar token.", exception);
        }
    }

    public String getSubjectFromToken(String token) {
        try {
            // Define o algoritmo HMAC SHA256 para verificar a assinatura do token passando
            // a chave secreta definida
            Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);
            return JWT.require(algorithm)
                    .withIssuer(ISSUER) // Define o emissor do token
                    .build()
                    .verify(token) // Verifica a validade do token
                    .getSubject(); // Obtém o assunto (neste caso, o nome de usuário) do token
        } catch (JWTVerificationException exception) {
            throw new JWTVerificationException("Token inválido ou expirado.");
        }
    }

    private Instant creationDate() {
        return ZonedDateTime.now(ZoneId.of("America/Recife")).toInstant();
    }

    private Instant expirationDate() {
        return ZonedDateTime.now(ZoneId.of("America/Recife")).plusHours(4).toInstant();
    }
}
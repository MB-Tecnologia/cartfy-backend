package com.cartfy.backend.cartfy_backend.config;

import com.cartfy.backend.cartfy_backend.filter.UserAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
    @Autowired
    private UserAuthenticationFilter userAuthenticationFilter;

    public static final String [] ENDPOINTS_WITH_AUTHENTICATION_NOT_REQUIRED = {
        "/api/login", //url que usaremos para fazer login
        "/api/cadastro", //url que usaremos para criar um usuário        
        "/api/docs",
        "/api-docs",
        "/api/swagger-ui",
        "/api/swagger-ui/index.html",
        "/api/swagger-ui/swagger-ui.css",
        "/api/swagger-ui/index.css",
        "/api/swagger-ui/swagger-ui-bundle.js",
        "/api/swagger-ui/swagger-ui-standalone-preset.js",
        "/api/swagger-ui/swagger-initializer.js",
        "/api/swagger-ui/favicon-32x32.png",
        "/api/swagger-ui/favicon-16x16.png",
        "/api/swagger-ui/**",
        "/api-docs/swagger-config",
        "/api/users",
        "/api/produto/",
        "/api/produto/**",
        "/v2/api-docs/swagger-config",
        "/swagger-ui/index.html",
        "/swagger-ui/swagger-ui.css",
        "/swagger-ui/index.css",
        "/swagger-ui/swagger-ui-bundle.js",
        "/swagger-ui/swagger-ui-standalone-preset.js",
        "/swagger-ui/swagger-initializer.js",
        "/v3/api-docs/swagger-config",
        "/swagger-ui/favicon-32x32.png",
        "/swagger-ui/favicon-16x16.png",
        "/v3/api-docs",        
    };


    @Bean    
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity.csrf(csrf -> csrf.disable()) // Desativa a proteção contra CSRF
                .sessionManagement(
                    management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS)).authorizeHttpRequests(
                        requests -> requests // Habilita a autorização para as requisições HTTP
                        .requestMatchers(ENDPOINTS_WITH_AUTHENTICATION_NOT_REQUIRED).permitAll()
                        .requestMatchers("**/api/produto/**").permitAll()
                        .requestMatchers("**/swagger-ui/**").permitAll()
                        .requestMatchers("/v3/api-docs*/**").permitAll()
                        .anyRequest().authenticated()
                    ).addFilterBefore(userAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }    
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring()
          .requestMatchers("/swagger-ui/**", "/v3/api-docs*/**");
    }

}
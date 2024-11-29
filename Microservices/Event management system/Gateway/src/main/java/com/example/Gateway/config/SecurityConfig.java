//package com.example.Gateway.config;
//
//import com.example.Gateway.config.JwtAuthenticationFilter;
//import org.springframework.http.HttpMethod;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
//import org.springframework.security.config.web.server.ServerHttpSecurity;
//import org.springframework.security.web.server.SecurityWebFilterChain;
//
//@Configuration
//@EnableWebFluxSecurity
//public class SecurityConfig {
//
//    @Bean
//    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http, JwtAuthenticationFilter jwtAuthenticationFilter) {
//        return http
//                .csrf().disable()
//                .authorizeExchange()
//                .pathMatchers(HttpMethod.POST, "/users/login", "/users/register").permitAll()
//                .anyExchange().authenticated()
//                .and()
//                .addFilterAt(jwtAuthenticationFilter, SecurityWebFilterChain.FilterOrder.AUTHENTICATION)
//                .build();
//    }
//}
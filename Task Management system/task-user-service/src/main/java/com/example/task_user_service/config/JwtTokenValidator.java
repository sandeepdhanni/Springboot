package com.example.task_user_service.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.util.List;

public class JwtTokenValidator extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String jwt=request.getHeader(JwtContant.JWT_HEADER);
        if(jwt!=null){
            //Beared jwt_token - 7 words
            jwt=jwt.substring(7);
            try{
                SecretKey key= Keys.hmacShaKeyFor(JwtContant.SECRET_KEY.getBytes());
                Claims claims=Jwts.parser().setSigningKey(key).build().parseClaimsJwt(jwt).getBody();
                String email=String.valueOf(claims.get("email"));
                String authorities=String.valueOf(claims.get("authorities"));
                List<GrantedAuthority> auths= AuthorityUtils
            }catch (){

            }
        }
    }
}

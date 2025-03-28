package com.ml_service.controller;

import com.ml_service.dto.Userdto;
import com.ml_service.exception.InvalidUserException;
import com.ml_service.model.CommonResponse;
import com.ml_service.model.LoginRequest;
import com.ml_service.model.Response;
import com.ml_service.service.Userservice;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Autowired
    private Userservice userservice;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody Userdto userdto){
        String response = userservice.registerUser(userdto);

        return ResponseEntity.ok(response);
    }
    @PostMapping("/login")
    public ResponseEntity<Response<Object>> userLogin(@RequestBody LoginRequest login) throws InvalidUserException {

        try {
            if (authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(login.getEmail(), login.getPassword())).isAuthenticated()) {
                return new CommonResponse<>()
                        .prepareSuccessResponseObject(userservice.generateJwtToken(login.getEmail()), HttpStatus.OK);
            } else {
                throw new InvalidUserException("Invalid Credentials!!");
            }
        } catch (Exception e) {

            throw new InvalidUserException("Invalid Credentials!!");
        }
    }


}

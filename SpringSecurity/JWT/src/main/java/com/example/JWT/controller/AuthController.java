package com.example.JWT.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.JWT.entity.User;
import com.example.JWT.model.SignUp;
import com.example.JWT.service.AuthService;
import com.example.JWT.service.JwtService;

@RestController
@RequestMapping("/auth")
public class AuthController {
	
	@Autowired
	JwtService jwtService;
	
	@Autowired 
	AuthService service;
	
	 @PostMapping("/signup")
	    public ResponseEntity<User> signup(@RequestBody SignUp request) throws Exception {
		 return ResponseEntity.ok(service.saveUser(request));
	 }
	@PostMapping("/signin")
	public ResponseEntity<String> login(@RequestBody SignUp request) {
		User user = service.authenticate(request);
		return ResponseEntity.ok(jwtService.genrateToken(user));
	}
	
	    
	    @GetMapping("/test")
	    public ResponseEntity<String> testEndpoint() {
	        return ResponseEntity.ok("Test endpoint is accessible");
	    }

}
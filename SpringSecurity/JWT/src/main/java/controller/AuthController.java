package controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import entity.User;
import model.SignUp;
import service.AuthService;
import service.JwtService;

@RestController
@RequestMapping("/auth")
public class AuthController {
	
	@Autowired
	JwtService jwtService;
	
	@Autowired 
	AuthService service;
	
	 @PostMapping("/signup")
	    public ResponseEntity<String> signup(@RequestBody SignUp request) {
	        try {
	        	service.saveUser(request);
	            return ResponseEntity.ok("User registered successfully");
	        } catch (Exception e) {
	            return ResponseEntity.badRequest().body(e.getMessage());
	        }
	    }

	    @PostMapping("/signin")
	    public ResponseEntity<String> signin(@RequestBody SignUp request) {
	        User user = service.authenticate(request);
	        String token = jwtService.generateToken(user);
	        return ResponseEntity.ok(token);
	    }
	
	    
	    @GetMapping("/test")
	    public ResponseEntity<String> testEndpoint() {
	        return ResponseEntity.ok("Test endpoint is accessible");
	    }

}
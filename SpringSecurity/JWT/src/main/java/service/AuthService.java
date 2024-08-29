package service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import entity.User;
import exceptionhandling.UserExists;
import model.SignUp;
import repo.UserRepository;




@Service
public class AuthService {
	
	@Autowired
	UserRepository repository;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Autowired
	AuthenticationManager authManager;
	
	public User saveUser(SignUp request) throws Exception {

		if(repository.existsByName(request.getName())){
			throw new UserExists("This Username already registered");
		}

		User user = User.builder()
				.name(request.getName())
				.password(passwordEncoder.encode(request.getPassword())).build();
		return repository.save(user);
	}
	public User authenticate(SignUp request) {
		authManager.authenticate(
				new UsernamePasswordAuthenticationToken(
						request.getName(), 
						request.getPassword())
				);
		return repository.findByName(request.getName()).get();
	}

}

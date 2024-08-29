package com.example.SpringSecurityJWT;

import com.example.SpringSecurityJWT.entity.UserInfo;
import com.example.SpringSecurityJWT.repository.UserRepo;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SpringBootApplication
public class SpringSecurityJwtApplication {

	@Autowired
	private UserRepo repository;


	@PostConstruct
	public void initUsers(){
      List<UserInfo> users= Stream.of(
			  new UserInfo(101, "sandeep", "sandeep12", "sandeep@gmail.com"),
			  new UserInfo(101, "rohit", "rohit3", "rohit@gmail.com"),
			  new UserInfo(101, "ravi", "ravi12", "ravi@gmail.com"),
			  new UserInfo(101, "junaid", "junaid123", "junaid@gmail.com")
	  ).collect(Collectors.toList());
      repository.saveAll(users);
	}

	public static void main(String[] args) {
		SpringApplication.run(SpringSecurityJwtApplication.class, args);
	}

}

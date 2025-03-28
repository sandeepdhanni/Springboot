package com.ml_service.service;

import com.ml_service.dto.Userdto;
import com.ml_service.entity.UserEntity;
import com.ml_service.jwt.JwtService;
import com.ml_service.model.LoginResponse;
import com.ml_service.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserServiceImpl implements Userservice {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private MyUserDetailsService myUserDetailsService;

    @Override
    public String registerUser(Userdto userdto) {
        if (!userRepository.findByEmail(userdto.getEmail()).isPresent()) {
            UserEntity user = modelMapper.map(userdto, UserEntity.class);

            user.setPassword(passwordEncoder.encode(userdto.getPassword()));

            // Use the role from the Userdto, if provided, otherwise default to "USER"
            if (userdto.getRole() != null && !userdto.getRole().isEmpty()) {
                user.setRole(userdto.getRole());  // Use the role passed in the request
            } else {
                user.setRole("USER");  // Default to "USER" if no role is specified
            }

            userRepository.save(user);

            return "User registered successfully";
        } else {
            return "Email already exists";
        }

    }


    @Override
    public LoginResponse generateJwtToken(String email) {
        String jwtToken = jwtService.genarateJwtToken(myUserDetailsService.loadUserByUsername(email));
        String refreshToken = jwtService.genarateRefreshToken(myUserDetailsService.loadUserByUsername(email));

        UserEntity userEntity = userRepository.findByEmail(email).get();

        LoginResponse response = new LoginResponse();
        response.setJwtToken(jwtToken);
        response.setRefreshToken(refreshToken);
        response.setUserEntity(userEntity);

        return response;
    }


    @Override
    public List<Userdto> getAllUsers() {
        return  userRepository.findAll().stream()
                .map(user -> new Userdto(
                        user.getFirstname(),
                        user.getLastname(),
                        null,
                        user.getEmail(),
                        user.getPhoneNumber(),
                        user.getRole()
                )).collect(Collectors.toList());
    }
}

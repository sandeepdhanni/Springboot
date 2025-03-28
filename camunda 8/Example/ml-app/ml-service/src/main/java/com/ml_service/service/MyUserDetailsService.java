package com.ml_service.service;

import com.ml_service.entity.UserEntity;
import com.ml_service.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class MyUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserEntity> userOpt = userRepository.findByEmail(username);

        if (userOpt.isPresent()) {
            UserEntity userEntity = userOpt.get();

            // Set role as authority (e.g., ROLE_USER or ROLE_ADMIN)
            String role = userEntity.getRole();  // Ensure role is stored in the user entity

            // Create Spring Security User object with role as authority
            UserDetails user = User.builder()
                    .username(userEntity.getEmail())
                    .password(userEntity.getPassword())
                    .authorities("ROLE_" + role) // Ensure "ROLE_" prefix
                    .build();

            System.out.println("User: " + userEntity.getEmail() + " | Assigned Role: " + "ROLE_" + role);
            return user;
        } else {
            throw new UsernameNotFoundException("User not found with email: " + username);
        }
    }
}

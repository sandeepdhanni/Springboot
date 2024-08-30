package com.example.JWTRefreshToken.repo;

import java.util.Optional;

import com.example.JWTRefreshToken.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, Integer>{

    Optional<User> findByName(String name);
    boolean existsByName(String name);

}
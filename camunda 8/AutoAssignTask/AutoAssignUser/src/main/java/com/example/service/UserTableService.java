package com.example.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.UserTable;
import com.example.repository.UserTableRepository;

@Service
public class UserTableService {

    @Autowired
    private UserTableRepository userTableRepository;

    // Create operation
    public UserTable createUser(UserTable user) {
        return userTableRepository.save(user);
    }

    // Read operation (Get all users)
    public List<UserTable> getAllUsers() {
        return userTableRepository.findAll();
    }

    // Read operation (Get user by ID)
    public Optional<UserTable> getUserById(Long id) {
        return userTableRepository.findById(id);
    }

    // Update operation
    public UserTable updateUser(Long id, UserTable userDetails) {
        return userTableRepository.findById(id).map(user -> {
            user.setUserName(userDetails.getUserName());
            user.setTaskCount(userDetails.getTaskCount());
            return userTableRepository.save(user);
        }).orElseThrow(() -> new RuntimeException("User not found"));
    }

    // Delete operation
    public void deleteUser(Long id) {
        userTableRepository.deleteById(id);
    }
}

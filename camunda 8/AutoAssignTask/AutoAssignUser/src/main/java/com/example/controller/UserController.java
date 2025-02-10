package com.example.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.entity.UserTable;
import com.example.service.UserTableService;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UserController {

    @Autowired
    private UserTableService userTableService;

    @PostMapping
    public ResponseEntity<UserTable> createUser(@RequestBody UserTable user) {
        return ResponseEntity.ok(userTableService.createUser(user));
    }

    @GetMapping
    public ResponseEntity<List<UserTable>> getAllUsers() {
        return ResponseEntity.ok(userTableService.getAllUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<UserTable>> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userTableService.getUserById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserTable> updateUser(@PathVariable Long id, @RequestBody UserTable user) {
        return ResponseEntity.ok(userTableService.updateUser(id, user));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userTableService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}

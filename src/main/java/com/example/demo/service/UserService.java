package com.example.demo.service;

import com.example.demo.model.User;
import com.example.demo.repositoryk.UserRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepo userRepo;

    // CREATE
    public User createUser(User user) {
        return userRepo.save(user);
    }

    // READ - Get by ID
    public User getUserById(Long id) {
        return userRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
    }

    // READ - Get all users
    public List<User> getAllUsers() {
        return userRepo.findAll();
    }

    // UPDATE
    public User updateUser(Long id, User updatedUser) {
        User existingUser = getUserById(id); // find or throw
        existingUser.setUsername(updatedUser.getUsername());
        existingUser.setPassword(updatedUser.getPassword());
        existingUser.setRole(updatedUser.getRole());
        return userRepo.save(existingUser);
    }

    // DELETE
    public void deleteUser(Long id) {
        if (!userRepo.existsById(id)) {
            throw new RuntimeException("Cannot delete — user not found with id: " + id);
        }
        userRepo.deleteById(id);
    }
}

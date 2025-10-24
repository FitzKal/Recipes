package com.example.demo.service;

import com.example.demo.Converters.BasicUserDtoConverter;
import com.example.demo.DTOs.BasicUserDto;
import com.example.demo.repositories.UserRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserService {

    private UserRepo userRepo;
    private BasicUserDtoConverter basicUserDtoConverter;

    // CREATE
    public BasicUserDto saveUser(BasicUserDto basicUserDto) {
        var userToSave = basicUserDtoConverter.convertBasicUserDtoToUser(basicUserDto);
        userRepo.save(userToSave);
        return basicUserDto;
    }

    // READ - Get by ID
    public BasicUserDto getUserById(Long id) {
        return userRepo.findById(id)
                .map(basicUserDtoConverter::convertUserToBasicUserDto)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
    }

    // READ - Get all users
    public List<BasicUserDto> getAllUsers() {
        return userRepo.findAll().stream().map(basicUserDtoConverter::convertUserToBasicUserDto)
                .toList();
    }

    // UPDATE
    public BasicUserDto updateUser(Long id, BasicUserDto updatedUser) {
        var existingUser = getUserById(id);
        existingUser.setUsername(updatedUser.getUsername());
        existingUser.setPassword(updatedUser.getPassword());
        saveUser(existingUser);
        return existingUser;
    }

    // DELETE
    public void deleteUser(Long id) {
        if (!userRepo.existsById(id)) {
            throw new RuntimeException("Cannot delete — user not found with id: " + id);
        }
        userRepo.deleteById(id);
    }
}

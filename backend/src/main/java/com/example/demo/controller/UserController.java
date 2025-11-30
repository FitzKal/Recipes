package com.example.demo.controller;

import com.example.demo.DTOs.BasicUserDto;
import com.example.demo.service.UserService;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recipe/user")
@AllArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BasicUserDto> saveUser(@NonNull @RequestBody BasicUserDto basicUserDto){
        System.out.println(basicUserDto);
        return ResponseEntity.ok(userService.saveUser(basicUserDto));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BasicUserDto> getUserById(@NonNull @PathVariable Long id){
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<BasicUserDto>> getAllUsers(){
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BasicUserDto> updateUser(@NonNull @RequestBody BasicUserDto updatedUser,
                                                   @NonNull @PathVariable Long id){
        return ResponseEntity.ok(userService.updateUser(id,updatedUser));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteUser(@NonNull @PathVariable Long id){
        userService.deleteUser(id);
        return ResponseEntity.ok("User with the id of: " + id + "has been deleted");
    }
}

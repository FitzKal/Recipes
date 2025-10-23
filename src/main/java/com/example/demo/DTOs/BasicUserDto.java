package com.example.demo.DTOs;

import com.example.demo.model.Recipe;
import com.example.demo.model.Role;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@Builder
public class BasicUserDto {
    private Long userId;
    private String username;
    private String password;
    private List<Recipe> recipes;
    private Role role;
}

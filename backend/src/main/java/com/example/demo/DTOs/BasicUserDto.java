package com.example.demo.DTOs;

import com.example.demo.model.Recipe;
import com.example.demo.model.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class BasicUserDto {
    @JsonProperty("user_id")
    private Long userId;
    private String username;
    private String password;
    @JsonIgnore
    private List<Recipe> recipes;
    private Role role;
}

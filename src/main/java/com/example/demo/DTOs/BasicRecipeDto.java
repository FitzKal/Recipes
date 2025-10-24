package com.example.demo.DTOs;

import com.example.demo.model.Category;
import com.example.demo.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class BasicRecipeDto {
    private Long id;
    private String recipeTitle;
    private String description;
    private Category category;
    private String pictureSrc;
    private User user;
}

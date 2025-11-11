package com.example.demo.service;

import com.example.demo.Converters.BasicRecipeDtoConverter;
import com.example.demo.DTOs.BasicRecipeDto;
import com.example.demo.model.Recipe;
import com.example.demo.model.Role;
import com.example.demo.model.User;
import com.example.demo.repositories.RecipeRepo;
import com.example.demo.repositories.UserRepo;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class RecipeService {

    private RecipeRepo recipeRepo;
    private BasicRecipeDtoConverter basicRecipeDtoConverter;
    private JWTService jwtService;
    private UserRepo userRepo;

    //CREATE
    public BasicRecipeDto createRecipe(BasicRecipeDto basicRecipeDto, HttpServletRequest request) {
        var user = getUserFromRequest(request);
        var recipeToSave = basicRecipeDtoConverter.convertBasicRecipeDtoToRecipe(basicRecipeDto);
        user.addRecipe(recipeToSave);
        recipeRepo.save(recipeToSave);
        basicRecipeDto.setUsername(user.getUsername());
        return basicRecipeDto;
    }

    //READ - Get by ID
    public BasicRecipeDto getRecipeById(Long id) {
        var recipe = recipeRepo
                            .findById(id)
                            .orElseThrow(() -> new RuntimeException("Recipe not found with id: " + id));
        var response = basicRecipeDtoConverter.convertRecipeToBasicRecipeDto(recipe);
        response.setUsername(recipe.getUser().getUsername());
        return response;
    }

    //READ - Get all recipes
    public List<BasicRecipeDto> getAllRecipes() {
        return recipeRepo
                .findAll()
                .stream()
                .map(basicRecipeDtoConverter::convertRecipeToBasicRecipeDto)
                .toList();
    }

    //UPDATE
    public BasicRecipeDto updateRecipe(Long id, BasicRecipeDto updatedRecipe) {
        //var existingRecipe = getRecipeById(id);
        var existingRecipe = recipeRepo.findById(id).orElseThrow(
                ()-> new RuntimeException("Recipe not Found")
        );
        existingRecipe.setRecipeTitle(updatedRecipe.getRecipeTitle());
        existingRecipe.setDescription(updatedRecipe.getDescription());
        existingRecipe.setCategory(updatedRecipe.getCategory());
        existingRecipe.setPictureSrc(updatedRecipe.getPictureSrc());
        //createRecipe(existingRecipe);
        recipeRepo.save(existingRecipe);

        return updatedRecipe;
    }

    //DELETE

    public void deleteRecipe(Long id) {
        if (!recipeRepo.existsById(id)) {
            throw new RuntimeException("Recipe not found with id: " + id);
        }
        recipeRepo.deleteById(id);
    }

    private User getUserFromRequest(HttpServletRequest request) {
        var tokenFromRequest = jwtService.extractTokenFromRequest(request);
        var username = jwtService.getUsernameFromToken(tokenFromRequest);
        return userRepo.findByUsername(username);
    }

    private boolean checkOwnerShip(String username, Recipe recipe) {
        var user = userRepo.findByUsername(username);
        return (recipe.getUser().getUsername().equals(username)
                || user.getRole().equals(Role.ADMIN));
    }

}

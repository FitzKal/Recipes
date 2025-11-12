package com.example.demo.service;

import com.example.demo.Converters.BasicRecipeDtoConverter;
import com.example.demo.DTOs.BasicRecipeDto;
import com.example.demo.model.Recipe;
import com.example.demo.model.Role;
import com.example.demo.model.User;
import com.example.demo.repositories.RecipeRepo;
import com.example.demo.repositories.UserRepo;
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
                .map(recipe -> {
                    var response = basicRecipeDtoConverter.convertRecipeToBasicRecipeDto(recipe);
                    response.setUsername(recipe.getUser().getUsername());
                    return response;
                })
                .toList();
    }

    //UPDATE
    public BasicRecipeDto updateRecipe(Long id, BasicRecipeDto updatedRecipe, HttpServletRequest request) {
       var recipeToUpdate = recipeRepo.findById(id).orElseThrow(()->new RuntimeException("Recipe not found"));

       var user = getUserFromRequest(request);
       if (checkOwnerShip(user.getUsername(),recipeToUpdate)){
           recipeToUpdate.setRecipeTitle(updatedRecipe.getRecipeTitle());
           recipeToUpdate.setDescription(updatedRecipe.getDescription());
           recipeToUpdate.setIngredients(updatedRecipe.getIngredients());
           recipeToUpdate.setInstructions(updatedRecipe.getInstructions());
           recipeToUpdate.setCategory(updatedRecipe.getCategory());
           recipeToUpdate.setPictureSrc(updatedRecipe.getPictureSrc());
           recipeRepo.save(recipeToUpdate);
           return updatedRecipe;
       }else {
           throw new RuntimeException(
                   "You are not the owner of the recipe, or don't have permission to edit the recipe"
           );
       }
    }

    //DELETE
    public void deleteRecipe(Long id, HttpServletRequest request) {
        var recipeToDelete = recipeRepo.findById(id).orElseThrow(() -> new RuntimeException("Recipe was not found"));
        var user = getUserFromRequest(request);
        if (checkOwnerShip(user.getUsername(),recipeToDelete)){
            recipeRepo.deleteById(id);
        }else {
            throw new RuntimeException("The delete was not successful");
        }
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

package com.example.demo.service;

import com.example.demo.Converters.BasicRecipeDtoConverter;
import com.example.demo.DTOs.BasicRecipeDto;
import com.example.demo.repositories.RecipeRepo;
import com.example.demo.repositories.UserRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class RecipeService {

    private RecipeRepo recipeRepo;
    private BasicRecipeDtoConverter basicRecipeDtoConverter;
    private UserRepo userRepo;

    //CREATE
    public BasicRecipeDto createRecipe(BasicRecipeDto basicRecipeDto,String username) {
        var owner = userRepo.findByUsername(username);
        System.out.println(owner);
        var recipeToSave = basicRecipeDtoConverter.convertBasicRecipeDtoToRecipe(basicRecipeDto);
        owner.addRecipe(recipeToSave);
        recipeRepo.save(recipeToSave);
        return basicRecipeDto;
    }

    //READ - Get by ID
    public BasicRecipeDto getRecipeById(Long id) {
        return recipeRepo
                .findById(id)
                .map(basicRecipeDtoConverter::convertRecipeToBasicRecipeDto)
                .orElseThrow(() -> new RuntimeException("Recipe not found with id: " + id));
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

}

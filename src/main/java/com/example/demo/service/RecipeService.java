package com.example.demo.service;

import com.example.demo.Converters.BasicRecipeDtoConverter;
import com.example.demo.DTOs.BasicRecipeDto;
import com.example.demo.repositories.RecipeRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class RecipeService {

    private RecipeRepo recipeRepo;
    private BasicRecipeDtoConverter basicRecipeDtoConverter;

    //CREATE
    public BasicRecipeDto createRecipe(BasicRecipeDto basicRecipeDto) {
        var recipeToSave = basicRecipeDtoConverter.convertBasicRecipeDtoToRecipe(basicRecipeDto);
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
        var existingRecipe = getRecipeById(id);
        existingRecipe.setRecipeTitle(updatedRecipe.getRecipeTitle());
        existingRecipe.setDescription(updatedRecipe.getDescription());
        existingRecipe.setCategory(updatedRecipe.getCategory());
        existingRecipe.setPictureSrc(updatedRecipe.getPictureSrc());
        createRecipe(existingRecipe);
        return existingRecipe;
    }

    //DELETE

    public void deleteRecipe(Long id) {
        if (!recipeRepo.existsById(id)) {
            throw new RuntimeException("Recipe not found with id: " + id);
        }
        recipeRepo.deleteById(id);
    }

}

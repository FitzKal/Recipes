package com.example.demo.controller;

import com.example.demo.DTOs.BasicRecipeDto;
import com.example.demo.service.RecipeService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recipes")
@AllArgsConstructor
public class RecipeController {
    private final RecipeService recipeService;

    @PostMapping
    public ResponseEntity<BasicRecipeDto> createRecipe(@NonNull @RequestBody BasicRecipeDto basicRecipeDto, HttpServletRequest request) {
        System.out.println(basicRecipeDto);
        return ResponseEntity.ok(recipeService.createRecipe(basicRecipeDto,request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BasicRecipeDto> getRecipeById(@NonNull @PathVariable Long id) {
        return ResponseEntity.ok(recipeService.getRecipeById(id));
    }

    @GetMapping
    public ResponseEntity<List<BasicRecipeDto>> getAllRecipes() {
        return ResponseEntity.ok(recipeService.getAllRecipes());
    }

    @PutMapping("/{id}")
    public ResponseEntity<BasicRecipeDto> updateRecipe(@NonNull @PathVariable Long id,
                                                       @NonNull @RequestBody BasicRecipeDto updateRecipe) {
        return ResponseEntity.ok(recipeService.updateRecipe(id, updateRecipe));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteRecipe(@NonNull @PathVariable Long id) {
        recipeService.deleteRecipe(id);
        return ResponseEntity.ok("Recipe with the id of:" + id + "has been deleted");
    }

}

package com.example.demo.Converters;

import com.example.demo.DTOs.BasicRecipeDto;
import com.example.demo.model.Category;
import com.example.demo.model.Recipe;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.*;

class BasicRecipeDtoConverterTest {

    private final BasicRecipeDtoConverter converter =
            Mappers.getMapper(BasicRecipeDtoConverter.class);

    @Test
    void convertRecipeToBasicRecipeDto_validRecipe_mappedCorrectly() {

        //Arrange
        Recipe recipe = new Recipe();
        recipe.setRecipeTitle("Test Recipe");
        recipe.setDescription("Test description");
        recipe.setIngredients("eggs, milk, sugar");
        recipe.setInstructions("mix, bake, serve");
        recipe.setCategory(Category.DESSERT);
        recipe.setPictureSrc("http://example.com/pic.jpg");

        //Act
        BasicRecipeDto result = converter.convertRecipeToBasicRecipeDto(recipe);

        //Assert
        assertNotNull(result);
        assertEquals("Test Recipe", result.getRecipeTitle());
        assertEquals("Test description", result.getDescription());
        assertEquals("eggs, milk, sugar", result.getIngredients());
        assertEquals("mix, bake, serve", result.getInstructions());
        assertEquals(Category.DESSERT, result.getCategory());
        assertEquals("http://example.com/pic.jpg", result.getPictureSrc());
    }

    @Test
    void convertBasicRecipeDtoToRecipe_validDto_mappedCorrectly() {

        //Arrange
        BasicRecipeDto dto = new BasicRecipeDto();
        dto.setRecipeTitle("Another Recipe");
        dto.setDescription("Another description");
        dto.setIngredients("flour, water");
        dto.setInstructions("knead, bake");
        dto.setCategory(Category.MAIN);
        dto.setPictureSrc("http://example.com/another.jpg");

        //Act
        Recipe result = converter.convertBasicRecipeDtoToRecipe(dto);

        //Assert
        assertNotNull(result);
        assertEquals("Another Recipe", result.getRecipeTitle());
        assertEquals("Another description", result.getDescription());
        assertEquals("flour, water", result.getIngredients());
        assertEquals("knead, bake", result.getInstructions());
        assertEquals(Category.MAIN, result.getCategory());
        assertEquals("http://example.com/another.jpg", result.getPictureSrc());
        // user field is ignored in the mapper, so we do not assert it here
    }
}

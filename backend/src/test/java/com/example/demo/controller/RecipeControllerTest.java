package com.example.demo.controller;

import com.example.demo.DTOs.BasicRecipeDto;
import com.example.demo.model.Category;
import com.example.demo.service.RecipeService;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RecipeControllerTest {

    @Mock
    private RecipeService recipeService;

    @Mock
    private HttpServletRequest request;

    @InjectMocks
    private RecipeController recipeController;


    @Test
    void createRecipe_validRequest_returnsOkResponse() {

        //Arrange
        BasicRecipeDto requestDto = new BasicRecipeDto();
        requestDto.setRecipeTitle("New Recipe");
        requestDto.setDescription("New description");
        requestDto.setCategory(Category.DESSERT);

        BasicRecipeDto responseDto = new BasicRecipeDto();
        responseDto.setRecipeTitle("New Recipe");
        responseDto.setDescription("New description");
        responseDto.setCategory(Category.DESSERT);

        when(recipeService.createRecipe(requestDto, request)).thenReturn(responseDto);

        //Act
        ResponseEntity<BasicRecipeDto> response = recipeController.createRecipe(requestDto, request);

        //Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("New Recipe", response.getBody().getRecipeTitle());
        assertEquals("New description", response.getBody().getDescription());
        assertEquals(Category.DESSERT, response.getBody().getCategory());

        verify(recipeService, times(1)).createRecipe(requestDto, request);
    }


    @Test
    void getRecipeById_existingId_returnsRecipe() {

        //Arrange
        Long id = 5L;

        BasicRecipeDto dto = new BasicRecipeDto();
        dto.setRecipeTitle("Existing Recipe");
        dto.setDescription("Desc");
        dto.setCategory(Category.SOUP);

        when(recipeService.getRecipeById(id)).thenReturn(dto);

        //Act
        ResponseEntity<BasicRecipeDto> response = recipeController.getRecipeById(id);

        //Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Existing Recipe", response.getBody().getRecipeTitle());
        assertEquals(Category.SOUP, response.getBody().getCategory());

        verify(recipeService, times(1)).getRecipeById(id);
    }


    @Test
    void getAllRecipes_existingRecipes_returnsList() {

        //Arrange
        BasicRecipeDto dto1 = new BasicRecipeDto();
        dto1.setRecipeTitle("R1");
        dto1.setCategory(Category.MAIN);

        BasicRecipeDto dto2 = new BasicRecipeDto();
        dto2.setRecipeTitle("R2");
        dto2.setCategory(Category.DESSERT);

        List<BasicRecipeDto> recipes = List.of(dto1, dto2);

        when(recipeService.getAllRecipes()).thenReturn(recipes);

        //Act
        ResponseEntity<List<BasicRecipeDto>> response = recipeController.getAllRecipes();

        //Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
        assertEquals("R1", response.getBody().get(0).getRecipeTitle());
        assertEquals("R2", response.getBody().get(1).getRecipeTitle());

        verify(recipeService, times(1)).getAllRecipes();
    }


    @Test
    void updateRecipe_validRequest_returnsUpdatedRecipe() {

        //Arrange
        Long id = 10L;

        BasicRecipeDto updateDto = new BasicRecipeDto();
        updateDto.setRecipeTitle("Updated");
        updateDto.setDescription("Updated desc");
        updateDto.setCategory(Category.DRINK);

        BasicRecipeDto updatedResult = new BasicRecipeDto();
        updatedResult.setRecipeTitle("Updated");
        updatedResult.setDescription("Updated desc");
        updatedResult.setCategory(Category.DRINK);

        when(recipeService.updateRecipe(id, updateDto, request)).thenReturn(updatedResult);

        //Act
        ResponseEntity<BasicRecipeDto> response =
                recipeController.updateRecipe(id, updateDto, request);

        //Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Updated", response.getBody().getRecipeTitle());
        assertEquals(Category.DRINK, response.getBody().getCategory());

        verify(recipeService, times(1)).updateRecipe(id, updateDto, request);
    }


    @Test
    void deleteRecipe_existingId_returnsOkMessage() {

        //Arrange
        Long id = 7L;

        //Act
        ResponseEntity<String> response = recipeController.deleteRecipe(id, request);

        //Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Recipe with the id of: 7 has been deleted", response.getBody());
        verify(recipeService, times(1)).deleteRecipe(id, request);
    }


    @Test
    void userBooks_validRequest_returnsUserRecipes() {

        //Arrange
        BasicRecipeDto dto1 = new BasicRecipeDto();
        dto1.setRecipeTitle("My R1");

        BasicRecipeDto dto2 = new BasicRecipeDto();
        dto2.setRecipeTitle("My R2");

        List<BasicRecipeDto> myRecipes = List.of(dto1, dto2);

        when(recipeService.getUserRecipe(request)).thenReturn(myRecipes);

        //Act
        ResponseEntity<List<BasicRecipeDto>> response = recipeController.userBooks(request);

        //Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
        assertEquals("My R1", response.getBody().get(0).getRecipeTitle());
        assertEquals("My R2", response.getBody().get(1).getRecipeTitle());

        verify(recipeService, times(1)).getUserRecipe(request);
    }
}

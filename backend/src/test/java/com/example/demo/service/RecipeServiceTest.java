package com.example.demo.service;

import com.example.demo.Converters.BasicRecipeDtoConverter;
import com.example.demo.DTOs.BasicRecipeDto;
import com.example.demo.model.Category;
import com.example.demo.model.Recipe;
import com.example.demo.model.Role;
import com.example.demo.model.User;
import com.example.demo.repositories.RecipeRepo;
import com.example.demo.repositories.UserRepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RecipeServiceTest {

    @Mock
    private RecipeRepo recipeRepo;

    @Mock
    private BasicRecipeDtoConverter basicRecipeDtoConverter;

    @Mock
    private UserRepo userRepo;

    @InjectMocks
    private RecipeService recipeService;

    @Captor
    private ArgumentCaptor<Recipe> recipeCaptor;


    // CREATE RECIPE TESTS

    @Test
    void createRecipe_success() {

        //Arrange
        String username = "random_user_01";

        User owner = new User();
        owner.setUsername(username);
        owner.setPassword("123456789");
        owner.setRole(Role.USER);
        owner.setRecipes(new ArrayList<>());
        userRepo.save(owner);


        BasicRecipeDto recipeToCreate = new BasicRecipeDto();
        recipeToCreate.setRecipeTitle("Test Recipe 01");
        recipeToCreate.setDescription("This is a recipe description for Test Recipe 01");
        recipeToCreate.setCategory(Category.DESSERT);
        recipeToCreate.setPictureSrc("http://example/picture.jpg");
        recipeToCreate.setUser(owner);

        Recipe converted = new Recipe();
        converted.setRecipeTitle("Test Recipe 01");
        converted.setDescription("This is a recipe description for Test Recipe 01");
        converted.setCategory(Category.DESSERT);
        converted.setPictureSrc("http://example/picture.jpg");

        when(userRepo.findByUsername(username)).thenReturn(owner);
        when(basicRecipeDtoConverter.convertBasicRecipeDtoToRecipe(recipeToCreate)).thenReturn(converted);

        //Act
        BasicRecipeDto createdRecipe = recipeService.createRecipe(recipeToCreate,username);

        //Assert
        assertNotNull(createdRecipe);
        assertEquals("Test Recipe 01", createdRecipe.getRecipeTitle());
        assertEquals("This is a recipe description for Test Recipe 01", createdRecipe.getDescription());
        assertEquals("http://example/picture.jpg", createdRecipe.getPictureSrc());
        assertEquals(Category.DESSERT, createdRecipe.getCategory());
        assertEquals("http://example/picture.jpg", createdRecipe.getPictureSrc());
        assertEquals(owner, createdRecipe.getUser());

        verify(userRepo, times(1)).findByUsername(username);
        verify(basicRecipeDtoConverter, times(1)).convertBasicRecipeDtoToRecipe(recipeToCreate);
        verify(recipeRepo, times(1)).save(recipeCaptor.capture());

        Recipe capturedRecipe = recipeCaptor.getValue();
        assertEquals("Test Recipe 01", capturedRecipe.getRecipeTitle());
        assertEquals("This is a recipe description for Test Recipe 01", capturedRecipe.getDescription());
        assertEquals(Category.DESSERT, capturedRecipe.getCategory());
        assertEquals("http://example/picture.jpg", capturedRecipe.getPictureSrc());
        assertEquals(owner, capturedRecipe.getUser());
    }


    @Test
    void createRecipe_failure() {

        // Arrange
        BasicRecipeDto recipeToCreate = new BasicRecipeDto();
        recipeToCreate.setRecipeTitle("Test Recipe 02");
        recipeToCreate.setDescription("This is a recipe description for Test Recipe 02");
        recipeToCreate.setCategory(Category.DESSERT);
        recipeToCreate.setPictureSrc("http://example/picture_02.jpg");

        when(userRepo.findByUsername("invalid_username")).thenReturn(null);

        // Act + Assert
        assertThrows(NullPointerException.class, () ->
                recipeService.createRecipe(recipeToCreate, "invalid_username")
        );

        verify(userRepo, times(1)).findByUsername("invalid_username");
        verify(recipeRepo, never()).save(any());
    }

    // GET RECIPE BY ID TESTS

    @Test
    void getRecipeById_success() {

        //Arrange
        Long recipeId = 13L;

        Recipe recipeInRepo = new Recipe();
        recipeInRepo.setId(recipeId);
        recipeInRepo.setRecipeTitle("Test Recipe 03");
        recipeInRepo.setDescription("This is a recipe description for Test Recipe 03");
        recipeInRepo.setCategory(Category.SOUP);
        recipeInRepo.setPictureSrc("http://example/picture_03.jpg");

        BasicRecipeDto convertedDto = new BasicRecipeDto();
        convertedDto.setRecipeTitle("Test Recipe 03");
        convertedDto.setDescription("This is a recipe description for Test Recipe 03");
        convertedDto.setCategory(Category.SOUP);
        convertedDto.setPictureSrc("http://example/picture_03.jpg");

        when(recipeRepo.findById(recipeId)).thenReturn(Optional.of(recipeInRepo));
        when(basicRecipeDtoConverter.convertRecipeToBasicRecipeDto(recipeInRepo)).thenReturn(convertedDto);

        //Act
        BasicRecipeDto result = recipeService.getRecipeById(recipeId);

        //Assert
        assertNotNull(result);
        assertEquals("Test Recipe 03", result.getRecipeTitle());
        assertEquals("This is a recipe description for Test Recipe 03", result.getDescription());
        assertEquals("http://example/picture_03.jpg", result.getPictureSrc());
        assertEquals(Category.SOUP, result.getCategory());
        assertEquals("http://example/picture_03.jpg", result.getPictureSrc());

        verify(recipeRepo, times(1)).findById(recipeId);
        verify(basicRecipeDtoConverter, times(1)).convertRecipeToBasicRecipeDto(recipeInRepo);
    }

    @Test
    void getRecipeById_failure() {

        // Arrange
        Long invalidId = 999L;
        when(recipeRepo.findById(invalidId)).thenReturn(Optional.empty());

        // Act + Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                recipeService.getRecipeById(invalidId)
        );

        assertEquals("Recipe not found with id: 999", exception.getMessage());
        verify(recipeRepo, times(1)).findById(invalidId);
        verify(basicRecipeDtoConverter, never()).convertRecipeToBasicRecipeDto(any());
    }


    // GET ALL RECIPES

    @Test
    void getAllRecipes_success() {

        // Arrange
        Recipe recipe1 = new Recipe();
        recipe1.setRecipeTitle("Test Recipe 04");
        recipe1.setDescription("This is a recipe description for Test Recipe 04");
        recipe1.setCategory(Category.SOUP);
        recipe1.setPictureSrc("http://example/picture_04.jpg");

        Recipe recipe2 = new Recipe();
        recipe2.setRecipeTitle("Test Recipe 04");
        recipe2.setDescription("This is a recipe description for Test Recipe 04");
        recipe2.setCategory(Category.MAIN_COURSE);
        recipe2.setPictureSrc("http://example/picture_04.jpg");

        when(recipeRepo.findAll()).thenReturn(List.of(recipe1, recipe2));

        BasicRecipeDto dto1 = new BasicRecipeDto();
        dto1.setRecipeTitle("Test Recipe 04");
        dto1.setDescription("This is a recipe description for Test Recipe 04");
        dto1.setCategory(Category.SOUP);
        dto1.setPictureSrc("http://example/picture_04.jpg");

        BasicRecipeDto dto2 = new BasicRecipeDto();
        dto2.setRecipeTitle("Test Recipe 04");
        dto2.setDescription("This is a recipe description for Test Recipe 04");
        dto2.setCategory(Category.MAIN_COURSE);
        dto2.setPictureSrc("http://example/picture_04.jpg");

        when(basicRecipeDtoConverter.convertRecipeToBasicRecipeDto(recipe1)).thenReturn(dto1);
        when(basicRecipeDtoConverter.convertRecipeToBasicRecipeDto(recipe2)).thenReturn(dto2);

        // Act
        List<BasicRecipeDto> result = recipeService.getAllRecipes();

        // Assert
        assertEquals(2, result.size());
        verify(recipeRepo, times(1)).findAll();
        verify(basicRecipeDtoConverter, times(2)).convertRecipeToBasicRecipeDto(any(Recipe.class));
    }

    @Test
    void getAllRecipes_failure() {

        // Arrange
        when(recipeRepo.findAll()).thenReturn(Collections.emptyList());

        // Act
        List<BasicRecipeDto> result = recipeService.getAllRecipes();

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty(), "Expected empty result list");
        verify(recipeRepo, times(1)).findAll();
        verify(basicRecipeDtoConverter, never()).convertRecipeToBasicRecipeDto(any());
    }


    // UPDATE RECIPE TESTS

    @Test
    void updateRecipe_success() {

        //Arrange
        Long recipeId = 42L;

        Recipe existingRecipe = new Recipe();
        existingRecipe.setId(recipeId);
        existingRecipe.setRecipeTitle("Old Title");

        BasicRecipeDto updatedDto = new BasicRecipeDto();
        updatedDto.setRecipeTitle("New Title");
        updatedDto.setDescription("Updated Description");
        updatedDto.setCategory(Category.MAIN_COURSE);
        updatedDto.setPictureSrc("http://example/new_pic.jpg");

        when(recipeRepo.findById(recipeId)).thenReturn(Optional.of(existingRecipe));

        // Act
        BasicRecipeDto result = recipeService.updateRecipe(recipeId, updatedDto);

        // Assert
        assertEquals("New Title", result.getRecipeTitle());
        assertEquals("Updated Description", result.getDescription());
        verify(recipeRepo, times(1)).findById(recipeId);
        verify(recipeRepo, times(1)).save(existingRecipe);
    }

    @Test
    void updateRecipe_failure() {

        // Arrange
        Long invalidId = 404L;
        BasicRecipeDto updatedRecipe = new BasicRecipeDto();
        updatedRecipe.setRecipeTitle("Updated Title");
        updatedRecipe.setDescription("Updated Description");
        updatedRecipe.setCategory(Category.DESSERT);
        updatedRecipe.setPictureSrc("http://example/updated.jpg");

        when(recipeRepo.findById(invalidId)).thenReturn(Optional.empty());

        // Act + Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                recipeService.updateRecipe(invalidId, updatedRecipe)
        );

        assertEquals("Recipe not Found", exception.getMessage());
        verify(recipeRepo, times(1)).findById(invalidId);
        verify(recipeRepo, never()).save(any());
    }


    // DELETE RECIPE TESTS

    @Test
    void deleteRecipe_success() {

        //Arrange
        Long recipeId = 10L;
        when(recipeRepo.existsById(recipeId)).thenReturn(true);

        //Act
        recipeService.deleteRecipe(recipeId);

        //Assert
        verify(recipeRepo, times(1)).existsById(recipeId);
        verify(recipeRepo, times(1)).deleteById(recipeId);
    }

    @Test
    void deleteRecipe_failure() {

        //Arrange
        Long recipeId = 99L;
        when(recipeRepo.existsById(recipeId)).thenReturn(false);

        //Act + Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                recipeService.deleteRecipe(recipeId)
        );

        assertEquals("Recipe not found with id: 99", exception.getMessage());
        verify(recipeRepo, times(1)).existsById(recipeId);
        verify(recipeRepo, never()).deleteById(any());
    }


}

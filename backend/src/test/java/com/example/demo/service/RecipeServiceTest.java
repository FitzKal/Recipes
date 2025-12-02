package com.example.demo.service;

import com.example.demo.Converters.BasicRecipeDtoConverter;
import com.example.demo.DTOs.BasicRecipeDto;
import com.example.demo.model.Category;
import com.example.demo.model.Recipe;
import com.example.demo.model.Role;
import com.example.demo.model.User;
import com.example.demo.repositories.RecipeRepo;
import com.example.demo.repositories.UserRepo;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RecipeServiceTest {

    @Mock
    private RecipeRepo recipeRepo;

    @Mock
    private BasicRecipeDtoConverter basicRecipeDtoConverter;

    @Mock
    private JWTService jwtService;

    @Mock
    private UserRepo userRepo;

    @Mock
    private HttpServletRequest request;

    @InjectMocks
    private RecipeService recipeService;

    private User createUser(String username, Role role) {
        User user = new User();
        user.setUsername(username);
        user.setRole(role);
        user.setRecipes(new java.util.ArrayList<>());
        return user;
    }

    private Recipe createRecipe(Long id, User owner) {
        Recipe recipe = new Recipe();
        recipe.setId(id);
        recipe.setRecipeTitle("Test Recipe");
        recipe.setDescription("Test description");
        recipe.setIngredients("ingredients");
        recipe.setInstructions("instructions");
        recipe.setCategory(Category.DESSERT); // létező kategória
        recipe.setPictureSrc("http://example.com/pic.jpg");
        recipe.setUser(owner);
        return recipe;
    }

    private BasicRecipeDto createDto() {
        BasicRecipeDto dto = new BasicRecipeDto();
        dto.setRecipeTitle("Test Recipe");
        dto.setDescription("Test description");
        dto.setIngredients("ingredients");
        dto.setInstructions("instructions");
        dto.setCategory(Category.DESSERT);
        dto.setPictureSrc("http://example.com/pic.jpg");
        return dto;
    }

    private void mockRequestUser(String token, String username, User user) {
        when(jwtService.extractTokenFromRequest(request)).thenReturn(token);
        when(jwtService.getUsernameFromToken(token)).thenReturn(username);
        when(userRepo.findByUsername(username)).thenReturn(user);
    }

    // ---------- CREATE ----------

    @Test
    void createRecipe_validData_returnsDtoWithUsernameAndSavesRecipe() {
        // Arrange
        User user = createUser("random_user_01", Role.USER);
        BasicRecipeDto dto = createDto();
        Recipe recipeEntity = createRecipe(1L, user);

        mockRequestUser("token", "random_user_01", user);
        when(basicRecipeDtoConverter.convertBasicRecipeDtoToRecipe(dto))
                .thenReturn(recipeEntity);

        // Act
        BasicRecipeDto createdRecipe = recipeService.createRecipe(dto, request);

        // Assert
        assertNotNull(createdRecipe);
        assertEquals("Test Recipe", createdRecipe.getRecipeTitle());
        assertEquals("Test description", createdRecipe.getDescription());
        assertEquals(Category.DESSERT, createdRecipe.getCategory());
        assertEquals("http://example.com/pic.jpg", createdRecipe.getPictureSrc());
        assertEquals("random_user_01", createdRecipe.getUsername());

        verify(basicRecipeDtoConverter, times(1)).convertBasicRecipeDtoToRecipe(dto);
        verify(recipeRepo, times(1)).save(recipeEntity);
    }

    @Test
    void createRecipe_invalidUser_throwsExceptionAndDoesNotSave() {
        // Arrange
        BasicRecipeDto dto = createDto();

        when(jwtService.extractTokenFromRequest(request)).thenReturn("token");
        when(jwtService.getUsernameFromToken("token")).thenReturn("invalid_username");
        when(userRepo.findByUsername("invalid_username")).thenReturn(null);

        // Act + Assert
        assertThrows(NullPointerException.class,
                () -> recipeService.createRecipe(dto, request));

        verify(recipeRepo, never()).save(any());
    }

    // ---------- GET BY ID ----------

    @Test
    void getRecipeById_correctId_returnRecipeDto() {
        // Arrange
        Long recipeId = 13L;

        User owner = createUser("owner", Role.USER);
        Recipe recipeInRepo = createRecipe(recipeId, owner);

        BasicRecipeDto convertedDto = createDto();

        when(recipeRepo.findById(recipeId)).thenReturn(Optional.of(recipeInRepo));
        when(basicRecipeDtoConverter.convertRecipeToBasicRecipeDto(recipeInRepo))
                .thenReturn(convertedDto);

        // Act
        BasicRecipeDto result = recipeService.getRecipeById(recipeId);

        // Assert
        assertNotNull(result);
        assertEquals("Test Recipe", result.getRecipeTitle());
        assertEquals("Test description", result.getDescription());
        assertEquals(Category.DESSERT, result.getCategory());
        assertEquals("owner", result.getUsername());

        verify(recipeRepo, times(1)).findById(recipeId);
        verify(basicRecipeDtoConverter, times(1))
                .convertRecipeToBasicRecipeDto(recipeInRepo);
    }

    @Test
    void getRecipeById_invalidId_throwsExceptionWithMessage() {
        // Arrange
        Long invalidId = 999L;
        when(recipeRepo.findById(invalidId)).thenReturn(Optional.empty());

        // Act + Assert
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> recipeService.getRecipeById(invalidId));

        assertEquals("Recipe not found with id: 999", exception.getMessage());
        verify(recipeRepo, times(1)).findById(invalidId);
        verify(basicRecipeDtoConverter, never()).convertRecipeToBasicRecipeDto(any());
    }

    // ---------- GET ALL ----------

    @Test
    void getAllRecipes_haveRecipes_returnRecipes() {
        // Arrange
        User u1 = createUser("user1", Role.USER);
        User u2 = createUser("user2", Role.USER);

        Recipe recipe1 = createRecipe(1L, u1);
        Recipe recipe2 = createRecipe(2L, u2);

        BasicRecipeDto dto1 = createDto();
        BasicRecipeDto dto2 = createDto();

        when(recipeRepo.findAll()).thenReturn(List.of(recipe1, recipe2));
        when(basicRecipeDtoConverter.convertRecipeToBasicRecipeDto(recipe1))
                .thenReturn(dto1);
        when(basicRecipeDtoConverter.convertRecipeToBasicRecipeDto(recipe2))
                .thenReturn(dto2);

        // Act
        List<BasicRecipeDto> result = recipeService.getAllRecipes();

        // Assert
        assertEquals(2, result.size());
        verify(recipeRepo, times(1)).findAll();
        verify(basicRecipeDtoConverter, times(2))
                .convertRecipeToBasicRecipeDto(any(Recipe.class));
    }

    @Test
    void getAllRecipes_noRecipes_returnEmptyList() {
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

    // ---------- UPDATE ----------

    @Test
    void updateRecipe_ownerUser_updatesAndReturnsDto() {
        // Arrange
        Long recipeId = 42L;
        User owner = createUser("owner_user", Role.USER);
        Recipe existingRecipe = createRecipe(recipeId, owner);

        BasicRecipeDto updatedDto = createDto();
        updatedDto.setRecipeTitle("New Title");
        updatedDto.setDescription("Updated Description");

        mockRequestUser("token", "owner_user", owner);
        when(recipeRepo.findById(recipeId)).thenReturn(Optional.of(existingRecipe));

        // Act
        BasicRecipeDto result = recipeService.updateRecipe(recipeId, updatedDto, request);

        // Assert
        assertEquals("New Title", existingRecipe.getRecipeTitle());
        assertEquals("Updated Description", existingRecipe.getDescription());
        assertSame(updatedDto, result);
        verify(recipeRepo, times(1)).findById(recipeId);
        verify(recipeRepo, times(1)).save(existingRecipe);
    }

    @Test
    void updateRecipe_noRecipeToUpdate_throwsException() {
        // Arrange
        Long invalidId = 404L;
        BasicRecipeDto updatedRecipe = createDto();

        when(recipeRepo.findById(invalidId)).thenReturn(Optional.empty());

        // Act + Assert
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> recipeService.updateRecipe(invalidId, updatedRecipe, request));

        assertEquals("Recipe not found", exception.getMessage());
        verify(recipeRepo, times(1)).findById(invalidId);
        verify(recipeRepo, never()).save(any());
    }

    @Test
    void updateRecipe_nonOwnerUser_throwsExceptionAndDoesNotSave() {
        // Arrange
        Long recipeId = 42L;
        User owner = createUser("owner_user", Role.USER);
        User other = createUser("other_user", Role.USER);
        Recipe existingRecipe = createRecipe(recipeId, owner);

        mockRequestUser("token", "other_user", other);
        when(recipeRepo.findById(recipeId)).thenReturn(Optional.of(existingRecipe));

        // Act + Assert
        assertThrows(RuntimeException.class,
                () -> recipeService.updateRecipe(recipeId, createDto(), request));
        verify(recipeRepo, never()).save(any());
    }

    // ---------- DELETE ----------

    @Test
    void deleteRecipe_ownerUser_deletesSuccessfully() {
        // Arrange
        Long recipeId = 10L;
        User owner = createUser("owner_user", Role.USER);
        Recipe existing = createRecipe(recipeId, owner);

        mockRequestUser("token", "owner_user", owner);
        when(recipeRepo.findById(recipeId)).thenReturn(Optional.of(existing));

        // Act
        recipeService.deleteRecipe(recipeId, request);

        // Assert
        verify(recipeRepo, times(1)).deleteById(recipeId);
    }

    @Test
    void deleteRecipe_noRecipeToDelete_throwsException() {
        // Arrange
        Long recipeId = 99L;

        //mockRequestUser("token", "owner_user", createUser("owner_user", Role.USER));
        when(recipeRepo.findById(recipeId)).thenReturn(Optional.empty());

        // Act + Assert
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> recipeService.deleteRecipe(recipeId, request));

        assertEquals("Recipe was not found", exception.getMessage());
        verify(recipeRepo, never()).deleteById(any());
    }

    @Test
    void deleteRecipe_nonOwnerUser_throwsExceptionAndDoesNotDelete() {
        // Arrange
        Long recipeId = 10L;
        User owner = createUser("owner_user", Role.USER);
        User other = createUser("other_user", Role.USER);
        Recipe existing = createRecipe(recipeId, owner);

        mockRequestUser("token", "other_user", other);
        when(recipeRepo.findById(recipeId)).thenReturn(Optional.of(existing));

        // Act + Assert
        assertThrows(RuntimeException.class,
                () -> recipeService.deleteRecipe(recipeId, request));
        verify(recipeRepo, never()).deleteById(anyLong());
    }

    // ---------- GET USER RECIPES ----------

    @Test
    void getUserRecipe_returnsOnlyRecipesOfLoggedInUser() {
        // Arrange
        User vera = createUser("vera", Role.USER);
        User bence = createUser("bence", Role.USER);

        Recipe r1 = createRecipe(1L, vera);
        Recipe r2 = createRecipe(2L, bence);
        Recipe r3 = createRecipe(3L, vera);

        BasicRecipeDto dto1 = createDto();
        BasicRecipeDto dto2 = createDto();
        BasicRecipeDto dto3 = createDto();

        mockRequestUser("token", "vera", vera);

        when(recipeRepo.findAll()).thenReturn(List.of(r1, r2, r3));
        when(basicRecipeDtoConverter.convertRecipeToBasicRecipeDto(r1)).thenReturn(dto1);
        when(basicRecipeDtoConverter.convertRecipeToBasicRecipeDto(r2)).thenReturn(dto2);
        when(basicRecipeDtoConverter.convertRecipeToBasicRecipeDto(r3)).thenReturn(dto3);

        // Act
        List<BasicRecipeDto> result = recipeService.getUserRecipe(request);

        // Assert
        assertEquals(2, result.size());
        assertTrue(result.stream().allMatch(r -> "vera".equals(r.getUsername())));
    }
}

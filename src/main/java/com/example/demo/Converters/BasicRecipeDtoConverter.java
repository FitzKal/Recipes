package com.example.demo.Converters;

import com.example.demo.DTOs.BasicRecipeDto;
import com.example.demo.model.Recipe;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BasicRecipeDtoConverter {
    BasicRecipeDto convertRecipeToBasicRecipeDto(Recipe recipe);
    Recipe convertBasicRecipeDtoToRecipe(BasicRecipeDto basicRecipeDto);
}

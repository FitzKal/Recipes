package com.example.demo.Converters;

import com.example.demo.DTOs.BasicRecipeDto;
import com.example.demo.model.Recipe;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BasicRecipeDtoConverter {
    BasicRecipeDto convertRecipeToBasicRecipeDto(Recipe recipe);
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "ingredients", ignore = false)
    @Mapping(target = "instructions", ignore = false)
    Recipe convertBasicRecipeDtoToRecipe(BasicRecipeDto basicRecipeDto);
}

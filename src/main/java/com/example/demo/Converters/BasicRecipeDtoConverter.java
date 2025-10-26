package com.example.demo.Converters;

import com.example.demo.DTOs.BasicRecipeDto;
import com.example.demo.model.Recipe;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BasicRecipeDtoConverter {
    BasicRecipeDto convertRecipeToBasicRecipeDto(Recipe recipe);
    @Mapping(target = "user", ignore = true)
    Recipe convertBasicRecipeDtoToRecipe(BasicRecipeDto basicRecipeDto);
}

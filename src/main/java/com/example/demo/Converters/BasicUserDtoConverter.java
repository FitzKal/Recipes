package com.example.demo.Converters;

import com.example.demo.DTOs.BasicUserDto;
import com.example.demo.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BasicUserDtoConverter {
    @Mapping(target = "recipes", ignore = true)
    BasicUserDto convertUserToBasicUserDto(User user);
    @Mapping(target = "recipes", ignore = true)
    User convertBasicUserDtoToUser(BasicUserDto basicUserDto);
}

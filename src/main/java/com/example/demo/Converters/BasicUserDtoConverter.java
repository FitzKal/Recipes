package com.example.demo.Converters;

import com.example.demo.DTOs.BasicUserDto;
import com.example.demo.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BasicUserDtoConverter {
    BasicUserDto convertUserToBasicUserDto(User user);
    User convertBasicUserDtoToUser(BasicUserDto basicUserDto);
}

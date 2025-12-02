package com.example.demo.service;

import com.example.demo.model.Role;
import com.example.demo.model.User;
import com.example.demo.model.UserPrinciple;
import com.example.demo.repositories.UserRepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomUserDetailServiceTest {

    @Mock
    private UserRepo userRepo;

    @InjectMocks
    private CustomUserDetailService customUserDetailService;

    @Test
    void loadUserByUsername_existingUser_returnsUserDetails() {
        User user = new User();
        user.setUsername("alpha");
        user.setPassword("12345");
        user.setRole(Role.USER);

        when(userRepo.findByUsername("alpha")).thenReturn(user);

        UserDetails result = customUserDetailService.loadUserByUsername("alpha");

        assertNotNull(result);
        assertEquals("alpha", result.getUsername());
    }

    @Test
    void loadUserByUsername_userNotFound_returnsUserPrincipleWithNullUser() {
        when(userRepo.findByUsername("ghost")).thenReturn(null);

        UserDetails result = customUserDetailService.loadUserByUsername("ghost");

        assertNotNull(result);
        assertTrue(result instanceof UserPrinciple);
    }
}

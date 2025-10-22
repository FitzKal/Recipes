package com.example.demo.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    // kapcsolat a Recipe entitáshoz (ha még nincs meg, majd később hozzáadod)
    private List<Recipe> recipes;

    @Enumerated(EnumType.STRING)
    private Role role;
}

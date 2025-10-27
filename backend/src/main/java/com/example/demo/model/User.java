package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
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
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long userId;

    @Column(nullable = false, unique = true)
    private String username;

    @Column
    private String password;

    @OneToMany (mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Recipe> recipes;

    @Enumerated(EnumType.STRING)
    private Role role;

    public void addRecipe(Recipe recipe){
        recipe.setUser(this);
        recipes.add(recipe);
    }

    public void removeRecipe(Recipe recipe){
        recipe.setUser(null);
        recipes.remove(recipe);
    }
}

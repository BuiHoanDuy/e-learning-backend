package com.example.start.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Builder
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "Users")
public class User {
    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = true, length = 100)
    private String fullName;

    @Column(nullable = true, unique = true, length = 100)
    private String email;

    @Column(nullable = false,unique = true, length = 255)
    private String username;

    @Column(nullable = false, length = 255)
    private String password;

    @ManyToMany
    Set<Role> roles;

    @Column(nullable = true)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(columnDefinition = "BOOLEAN DEFAULT false")
    @Builder.Default
    private  boolean isDeleted = false;
    @PrePersist
    public void prePersist() {
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
        if(fullName == null){
            fullName = username;
        }
    }
}
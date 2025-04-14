package com.example.start.entity;

import com.example.start.entity.User;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "Courses")
public class Course {
    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false, length = 200)
    private String courseName;

    @ManyToOne
    @JoinColumn(name = "teacherId", nullable = false)
    private User teacher;

    @ManyToMany
    private Set<User> student;

    private LocalDateTime createdAt;

    @Column(columnDefinition = "BOOLEAN DEFAULT false")
    @Builder.Default
    private  boolean isDeleted = false;

    @PrePersist
    private void prePersist(){
        createdAt = LocalDateTime.now();
    }
}

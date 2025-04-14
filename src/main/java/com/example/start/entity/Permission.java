package com.example.start.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class Permission {
    @Id
    String name;
    String description;
    @Column(columnDefinition = "BOOLEAN DEFAULT false")
    @Builder.Default
    private  boolean isDeleted = false;
    @PrePersist
    protected void onCreate() {
        if (name == null) {
            name = "DEFAULT_NAME"; // Gán nếu giá trị chưa được đặt
        }
    }
}

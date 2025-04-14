package com.example.start.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.id.GUIDGenerator;
import org.springframework.beans.factory.annotation.Value;

import java.util.Set;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class Role {
    @Id
    String name;
    String description;
    @ManyToMany(fetch = FetchType.EAGER)
    Set<Permission> permissions;

    @PrePersist
    protected void onCreate() {
        if (name == null) {
            name = "DEFAULT_NAME"; // Gán nếu giá trị chưa được đặt
        }
    }
    @Column(columnDefinition = "BOOLEAN DEFAULT false")
    @Builder.Default
    private  boolean isDeleted = false;
}

package com.example.start.dto.response;

import com.example.start.entity.Course;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AssignmentResponse {
    UUID id;
    String title;
    String description;
    LocalDateTime dueDate;
    LocalDateTime createdAt;
}

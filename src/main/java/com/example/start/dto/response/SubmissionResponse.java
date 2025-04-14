package com.example.start.dto.response;

import com.example.start.entity.Assignment;
import com.example.start.entity.User;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SubmissionResponse {
    private Assignment assignment;
    private User student;
    private List<String> submissionUrl;
    private LocalDateTime submittedAt;
}

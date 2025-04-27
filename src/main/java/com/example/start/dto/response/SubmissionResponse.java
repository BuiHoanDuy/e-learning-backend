package com.example.start.dto.response;

import com.example.start.entity.Assignment;
import com.example.start.entity.User;
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
    Assignment assignment;
    User student;
    List<String> submissionUrl;
    LocalDateTime submittedAt;
}

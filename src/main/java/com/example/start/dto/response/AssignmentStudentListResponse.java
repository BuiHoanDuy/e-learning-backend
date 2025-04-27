package com.example.start.dto.response;

import java.time.LocalDateTime;
import java.util.List;

public class AssignmentStudentListResponse {
    String fullName;
    String email;
    LocalDateTime submittedAt;
    boolean isSubmitted;
    List<String> submissionUrl;
}

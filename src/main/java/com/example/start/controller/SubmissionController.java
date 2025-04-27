package com.example.start.controller;

import com.example.start.dto.response.ApiResponse;
import com.example.start.dto.response.StudentSubmissionsResponse;
import com.example.start.dto.response.SubmissionResponse;
import com.example.start.dto.response.user.UserResponse;
import com.example.start.service.SubmissionService;
import com.example.start.service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/submissions")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class SubmissionController {
    SubmissionService submissionService;
    UserService userService;
    @PostMapping("/upload")
    public ApiResponse<SubmissionResponse> upload(@RequestParam UUID assignmentId, @RequestParam("file") List<MultipartFile> file) throws IOException {
        return ApiResponse.<SubmissionResponse>builder()
                .result(submissionService.create(assignmentId, file))
                .code(200)
                .build();
    }

    @GetMapping("/all-submissions/{assignmentId}")
    public ApiResponse<List<StudentSubmissionsResponse>> getAllSubmissionsForTeacher(@PathVariable UUID assignmentId) {
        return ApiResponse.<List<StudentSubmissionsResponse>>builder()
                .result(submissionService.getAllSubmission(assignmentId))
                .code(200)
                .build();
    }

    @GetMapping("/{assignmentId}/{studentId}")
    public ApiResponse<SubmissionResponse> getAllSubmissions(@PathVariable UUID assignmentId, @PathVariable UUID studentId) {
        return ApiResponse.<SubmissionResponse>builder()
                .result(submissionService.getSubmissionOfStudent(assignmentId, studentId))
                .code(200)
                .build();
    }
    @DeleteMapping("/{assignmentId}/{studentId}")
    public void  deleteSubmissions(@PathVariable UUID assignmentId, @PathVariable UUID studentId) {
        submissionService.deleteSubmissionOfStudent(assignmentId,studentId);
    }

    @GetMapping("students-without-submission/{assignmentId}")
    public ApiResponse<List<UserResponse>> getStudentsWithoutSubmissions(@PathVariable UUID assignmentId) {
        return ApiResponse.<List<UserResponse>>builder()
                .result(submissionService.getAllStudentWithoutSubmission(assignmentId))
                .code(200)
                .build();
    }

}

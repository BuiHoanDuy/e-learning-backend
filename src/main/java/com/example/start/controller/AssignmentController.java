package com.example.start.controller;

import com.example.start.dto.request.AssignmentRequest;
import com.example.start.dto.response.ApiResponse;
import com.example.start.dto.response.AssignmentResponse;
import com.example.start.service.AssignmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/assignments")
@RequiredArgsConstructor
public class AssignmentController {

    private final AssignmentService assignmentService;

    // Tạo bài tập mới
    @PostMapping("/{courseId}")
    public ApiResponse<AssignmentResponse> createAssignment(@PathVariable UUID courseId, @RequestBody AssignmentRequest request) {
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setResult(assignmentService.createAssignment(courseId, request));
        return apiResponse;
    }

    // Lấy chi tiết bài tập theo ID
    @GetMapping("/{id}")
    public ApiResponse<AssignmentResponse> getAssignmentById(@PathVariable UUID id) {
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setResult(assignmentService.getAssignmentById(id));
        return apiResponse;
    }

    // Lấy tất cả bài tập sắp đến hạn (chỉ những bài của người đang đăng nhập)
    @GetMapping
    public ApiResponse<List<AssignmentResponse>> getAllAssignments() {
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setResult(assignmentService.getAllAssignments());
        return apiResponse;
    }

    // Cập nhật bài tập
    @PutMapping("/{id}")
    public ApiResponse<AssignmentResponse> updateAssignment(
            @PathVariable UUID id,
            @RequestBody AssignmentRequest request) {
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setResult(assignmentService.updateAssignment(id, request));
        return apiResponse;
    }

    // Xóa bài tập
    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteAssignment(@PathVariable UUID id) {
        assignmentService.deleteAssignment(id);
        ApiResponse apiResponse = new ApiResponse();
        return apiResponse;
    }

    // Lấy danh sách bài tập theo khóa học
    @GetMapping("/by-course/{courseId}")
    public ApiResponse<List<AssignmentResponse>> getByCourseId(@PathVariable UUID courseId) {
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setResult(assignmentService.getAssignmentsByCourseId(courseId));
        return apiResponse;
    }

//    // Lấy danh sách bài tập theo username
//    @GetMapping("/by-username/{username}")
//    public ApiResponse<List<AssignmentResponse>> getByUsername(@PathVariable String username) {
//        List<AssignmentResponse> list = assignmentService.getAssignmentsByUsername(username);
//        return ResponseEntity.ok(list);
//    }
}

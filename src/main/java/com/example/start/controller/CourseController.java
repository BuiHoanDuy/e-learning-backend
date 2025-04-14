package com.example.start.controller;

import com.example.start.dto.request.CourseRequest;
import com.example.start.dto.response.ApiResponse;
import com.example.start.dto.response.CourseResponse;
import com.example.start.entity.Course;
import com.example.start.service.CourseService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/courses")
@RequiredArgsConstructor // Khởi tạo các private final attributes trong constructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class CourseController {
    CourseService courseService;

    @GetMapping
    ApiResponse<List<CourseResponse>> getAllCourse(){
        return ApiResponse.<List<CourseResponse>>builder()
                .result( courseService.getAllCourses())
                .build();
    }
    @GetMapping("/{id}")
    ApiResponse<CourseResponse> getCourseById(@PathVariable UUID id){
        return ApiResponse.<CourseResponse>builder()
                .result(courseService.getCourseById(id))
                .build();
    }
    @PostMapping
    ApiResponse<CourseResponse> createCourse(@RequestBody CourseRequest courseRequest){
        return ApiResponse.<CourseResponse>builder()
                .result( courseService.createCourse(courseRequest))
                .build();
    }
    @DeleteMapping("/{courseId}")
    ApiResponse<String> deleteCourse(@PathVariable UUID courseId){
        courseService.deleteCourse(courseId);
        return ApiResponse.<String>builder()
                .result("The course has been deleted")
                .build();
    }
    @PostMapping("/enrollments/{courseId}")
    ApiResponse<CourseResponse> enrollCourse (@PathVariable UUID courseId){
        return ApiResponse.<CourseResponse>builder()
                .result(courseService.enrollCourse(courseId))
                .build();
    }
}

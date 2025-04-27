package com.example.start.controller;

import com.example.start.dto.request.LessonRequest;
import com.example.start.dto.response.ApiResponse;
import com.example.start.dto.response.LessonResponse;
import com.example.start.service.LessonService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/lessons")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class LessonController {
    LessonService lessonService;

    @GetMapping("/{lessonId}")
    ApiResponse<LessonResponse> getLesson(@PathVariable UUID lessonId){
        return ApiResponse.<LessonResponse>builder()
                .code(200)
                .result(lessonService.getLessonById(lessonId))
                .build();
    }
    @GetMapping("/all/{courseId}")
    ApiResponse<List<LessonResponse>> getAllLessons(@PathVariable UUID courseId){
        return ApiResponse.<List<LessonResponse>>builder()
                .code(200)
                .result(lessonService.getLessonByCourseId(courseId))
                .build();
    }
    @PostMapping("/{courseId}")
    ApiResponse<LessonResponse> createLesson(@PathVariable UUID courseId,@RequestBody LessonRequest lessonRequest){
        return ApiResponse.<LessonResponse>builder()
                .code(200)
                .result(lessonService.createLesson(lessonRequest,courseId))
                .build();
    }
    @PutMapping("/{lessonId}")
    ApiResponse<LessonResponse> updateLesson(@PathVariable UUID lessonId,@RequestBody LessonRequest lessonRequest){
        return ApiResponse.<LessonResponse>builder()
                .code(200)
                .result(lessonService.updateLesson(lessonRequest,lessonId))
                .build();
    }
    @DeleteMapping("/{lessonId}")
    ApiResponse updateLesson(@PathVariable UUID lessonId){
        lessonService.deleteLesson(lessonId);
        return ApiResponse.builder().code(200).build();
    }
}

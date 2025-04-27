package com.example.start.service;

import com.example.start.dto.request.LessonRequest;
import com.example.start.dto.response.LessonResponse;
import com.example.start.entity.Course;
import com.example.start.entity.Lesson;
import com.example.start.exception.AppException;
import com.example.start.exception.ErrorCode;
import com.example.start.mapper.LessonMapper;
import com.example.start.repository.CourseRepository;
import com.example.start.repository.LessonRepository;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class LessonService {
    LessonRepository lessonRepository;
    LessonMapper lessonMapper;
    CourseRepository courseRepository;

    @PreAuthorize("@lessonSecurityService.hasAccessToLesson(#lessonId, authentication.name)")
    public LessonResponse getLessonById(UUID lessonId){
        return lessonMapper.toLessonResponse(lessonRepository
                .findById(lessonId).orElseThrow(()->new AppException(ErrorCode.LESSON_NOT_EXIST)));
    }

    @PreAuthorize("@courseSecurityService.hasAccessToCourse(#courseId, authentication.name)")
    public List<LessonResponse> getLessonByCourseId(UUID courseId){
        return lessonRepository.findAllByCourseIdAndIsDeleted(courseId, false)
                .stream().map(lessonMapper::toLessonResponse)
                .collect(Collectors.toList());
    }

    @PreAuthorize("@courseSecurityService.isTeacherOfCourse(#courseId, authentication.name)")
    public LessonResponse createLesson(LessonRequest lessonRequest, UUID courseId){
        Lesson lesson = lessonMapper.toLesson(lessonRequest);
        lesson.setCreatedAt(LocalDateTime.now());
        lesson.setCourse(courseRepository.findById(courseId).orElseThrow(
                ()->new AppException(ErrorCode.COURSE_NOT_EXIST)));
        return lessonMapper.toLessonResponse(lessonRepository.save(lesson));
    }
    @PreAuthorize("@lessonSecurityService.isAuthorOfLesson(#lessonId, authentication.name)")
    public LessonResponse updateLesson(LessonRequest lessonRequest, UUID lessonId){
        Lesson lesson = lessonRepository.findById(lessonId).orElseThrow(
                ()->new AppException(ErrorCode.LESSON_NOT_EXIST));
        lesson.setContent(lessonRequest.getContent());
        lesson.setTitle(lessonRequest.getTitle());
        lesson.setVideoUrl(lessonRequest.getVideoUrl());
        lesson.setCreatedAt(LocalDateTime.now());
        return lessonMapper.toLessonResponse(lessonRepository.save(lesson));
    }
    @PreAuthorize("@lessonSecurityService.isAuthorOfLesson(#lessonId, authentication.name)")
    public void deleteLesson(UUID lessonId){
        Lesson lesson = lessonRepository.findById(lessonId).orElseThrow(
                ()->new AppException(ErrorCode.LESSON_NOT_EXIST));
        lesson.setDeleted(true);
        lessonRepository.save(lesson);
    }
}

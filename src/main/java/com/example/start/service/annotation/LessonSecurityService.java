package com.example.start.service.annotation;

import com.example.start.repository.LessonRepository;
import com.example.start.repository.UserRepository;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PACKAGE, makeFinal = true)
public class LessonSecurityService {
    LessonRepository lessonRepository;
    UserRepository userRepository;

    public boolean hasAccessToLesson(UUID lessonId, String username) {
        return lessonRepository.findById(lessonId)
                .map(lesson -> lesson.getCourse().getTeacher().getUsername().equals(username) ||
                        lesson.getCourse().getStudent().stream().anyMatch(student -> student.getUsername().equals(username)))
                .orElse(false);
    }
    public boolean isAuthorOfLesson(UUID lessonId, String username) {
        log.info(lessonRepository.findById(lessonId)
                .map(lesson -> lesson.getCourse().getTeacher().getUsername()).toString());
        return lessonRepository.findById(lessonId)
                .map(lesson -> lesson.getCourse().getTeacher().getUsername().equals(username))
                .orElse(false);
    }
}

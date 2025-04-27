package com.example.start.service.annotation;

import com.example.start.entity.Course;
import com.example.start.repository.AssignmentRepository;
import com.example.start.repository.CourseRepository;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PACKAGE, makeFinal = true)
public class CourseSecurityService {

    CourseRepository courseRepository;
    AssignmentRepository assignmentRepository;

    public boolean isTeacherOfCourse(UUID courseId, String username) {
        return courseRepository.findById(courseId)
                .map(course -> course.getTeacher().getUsername().equals(username))
                .orElse(false);
    }

    public boolean isAuthorOfCourse(UUID assignmentId, String username) {
        return assignmentRepository.findById(assignmentId)
                .map(assignment -> {
                    Course course = assignment.getCourse();
                    return course.getTeacher().getUsername().equals(username);
                })
                .orElse(false);
    }

    public boolean hasAccessToAssignment(UUID assignmentId, String username) {
        return assignmentRepository.findById(assignmentId)
                .map(assignment -> {
                    Course course = assignment.getCourse();
                    return course.getTeacher().getUsername().equals(username) ||
                            course.getStudent().stream().anyMatch(student -> student.getUsername().equals(username));
                })
                .orElse(false);
    }

    public boolean hasAccessToCourse(UUID courseId, String username) {
        return courseRepository.findById(courseId)
                .map(course -> course.getTeacher().getUsername().equals(username) ||
                        course.getStudent().stream().anyMatch(student -> student.getUsername().equals(username)))
                .orElse(false);
    }
}
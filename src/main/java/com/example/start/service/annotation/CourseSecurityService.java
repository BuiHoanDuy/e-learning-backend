package com.example.start.service.annotation;

import com.example.start.entity.Course;
import com.example.start.repository.AssignmentRepository;
import com.example.start.repository.CourseRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CourseSecurityService {

    private final CourseRepository courseRepository;
    private final AssignmentRepository assignmentRepository;

    public CourseSecurityService(CourseRepository courseRepository, AssignmentRepository assignmentRepository) {
        this.courseRepository = courseRepository;
        this.assignmentRepository = assignmentRepository;
    }

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
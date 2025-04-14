package com.example.start.service;

import com.example.start.dto.request.CourseRequest;
import com.example.start.dto.response.CourseResponse;
import com.example.start.entity.Course;
import com.example.start.entity.Role;
import com.example.start.entity.User;
import com.example.start.exception.AppException;
import com.example.start.exception.ErrorCode;
import com.example.start.repository.CourseRepository;
import com.example.start.repository.RoleRepository;
import com.example.start.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
@Slf4j
@Service
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    //Get All Course Admin
    public List<CourseResponse> getAllCourses() {
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();
        User user = userRepository.findByUsername(name).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        List<Course> courses = new ArrayList<>();

        Optional<Role> studentRole = roleRepository.findById("STUDENT");
        Optional<Role> teacherRole = roleRepository.findById("TEACHER");
        Optional<Role> adminRole = roleRepository.findById("ADMIN");

        if (studentRole.isPresent() && user.getRoles().contains(studentRole.get())) {
            log.info(studentRole.get().toString());
            courses = courseRepository.findAllByIsDeletedAndStudentContains(false, user);
        } else if (teacherRole.isPresent() && user.getRoles().contains(teacherRole.get())) {
            log.info(teacherRole.get().toString());
            courses = courseRepository.findAllByIsDeletedAndTeacher(false, user);
        } else if (adminRole.isPresent() && user.getRoles().contains(adminRole.get())) {
            log.info("ADMIN NE: " + adminRole.get().toString());
            courses = courseRepository.findAllByIsDeleted(false);
        }

        return courses.stream().map(course -> new CourseResponse(
                course.getId(),
                course.getCourseName(),
                course.getTeacher(),
                course.getCreatedAt()
        )).collect(Collectors.toList());
    }

    public CourseResponse getCourseById(UUID id) {
        Course course = courseRepository.findById(id).orElseThrow(() -> new RuntimeException("Course not found"));
        return new CourseResponse(course.getId(), course.getCourseName(), course.getTeacher(), course.getCreatedAt());
    }

    @PreAuthorize("hasAuthority('AllPermissionForTeacher')")
    public CourseResponse createCourse(CourseRequest courseRequest) {
        User teacher = userRepository.findByUsername(userService.getMyInfo().getUsername())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        Course course = Course.builder()
                .courseName(courseRequest.getCourseName())
                .teacher(teacher)
                .build();
        Course savedCourse = courseRepository.save(course);
        return new CourseResponse(savedCourse.getId(), savedCourse.getCourseName(), savedCourse.getTeacher(), savedCourse.getCreatedAt());
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public void deleteCourse(UUID id) {
        Course course = courseRepository.findById(id).orElseThrow(() -> new RuntimeException("Course not found"));
        course.setDeleted(true);
        courseRepository.save(course);
    }

    @PreAuthorize("hasAuthority('AllPermissionForStudent')")
    public CourseResponse enrollCourse(UUID id) {
        User student = userRepository.findByUsername(userService.getMyInfo().getUsername())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        Course course = courseRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.COURSE_NOT_EXIST));

        Set<User> users = course.getStudent();
        users.add(student);
        course.setStudent(users);

        Course savedCourse = courseRepository.save(course);
        return new CourseResponse(savedCourse.getId(), savedCourse.getCourseName(), savedCourse.getTeacher(), savedCourse.getCreatedAt());
    }
}

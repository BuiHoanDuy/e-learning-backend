package com.example.start.service;

import com.example.start.dto.request.CourseRequest;
import com.example.start.dto.response.CourseResponse;
import com.example.start.entity.Course;
import com.example.start.entity.Role;
import com.example.start.entity.User;
import com.example.start.exception.AppException;
import com.example.start.exception.ErrorCode;
import com.example.start.mapper.CourseMapper;
import com.example.start.repository.CourseRepository;
import com.example.start.repository.RoleRepository;
import com.example.start.repository.UserRepository;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CourseService {
    CourseRepository courseRepository;
    UserService userService;
    UserRepository userRepository;
    RoleRepository roleRepository;
    CourseMapper courseMapper;

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
            log.info("ADMIN NE: " + adminRole.get());
            courses = courseRepository.findAllByIsDeleted(false);
        }
        return courses.stream().map(courseMapper::toCourseResponse).collect(Collectors.toList());
    }

    //Lấy danh sách sinh viên thuộc 1 khóa học nào đó
    public List<User> getStudentList(UUID id){
        return courseRepository.findAllUserByCourseId(id);
    }

    public CourseResponse getCourseById(UUID id) {
        Course course = courseRepository.findById(id).orElseThrow(() -> new RuntimeException("Course not found"));
        return courseMapper.toCourseResponse(course);
    }

    @PreAuthorize("hasAuthority('AllPermissionForTeacher')")
    public CourseResponse createCourse(CourseRequest courseRequest) {
        User teacher = userRepository.findByUsername(userService.getMyInfo().getUsername())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        String joinCode;
        do{
            joinCode = UUID.randomUUID().toString().substring(0,7).toUpperCase();
        } while (courseRepository.findByIsDeletedAndJoinCode(false,joinCode).isPresent());
        Course course = Course.builder()
                .courseName(courseRequest.getCourseName())
                .teacher(teacher)
                .joinCode(joinCode)
                .build();
        return courseMapper.toCourseResponse(courseRepository.save(course));
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

        return courseMapper.toCourseResponse(courseRepository.save(course));
    }

    public CourseResponse findCourseByJoinCode(String joinCode) {
        Course course = courseRepository.findByIsDeletedAndJoinCode(false,joinCode)
                .orElseThrow(() -> new AppException(ErrorCode.COURSE_NOT_EXIST));
        return courseMapper.toCourseResponse(course);
    }
}

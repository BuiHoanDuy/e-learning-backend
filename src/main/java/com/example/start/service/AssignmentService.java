package com.example.start.service;

import com.example.start.dto.request.AssignmentRequest;
import com.example.start.dto.response.AssignmentResponse;
import com.example.start.entity.Assignment;
import com.example.start.entity.Course;
import com.example.start.entity.Role;
import com.example.start.entity.User;
import com.example.start.exception.AppException;
import com.example.start.exception.ErrorCode;
import com.example.start.mapper.AssignmentMapper;
import com.example.start.repository.AssignmentRepository;
import com.example.start.repository.CourseRepository;
import com.example.start.repository.RoleRepository;
import com.example.start.repository.UserRepository;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AssignmentService {
    AssignmentRepository assignmentRepository;
    AssignmentMapper assignmentMapper;
    CourseRepository courseRepository;
    UserRepository userRepository;
    RoleRepository roleRepository;

    @PreAuthorize("@courseSecurityService.isTeacherOfCourse(#courseId, authentication.name)")
    public AssignmentResponse createAssignment(UUID courseId, AssignmentRequest assignmentRequest){
        Assignment assignment = assignmentMapper.toAssignment(assignmentRequest);
        assignment.setCourse(courseRepository.findById(courseId).orElseThrow(
                () -> new AppException(ErrorCode.ASSIGNMENT_NOT_EXIST)));
        assignmentRepository.save(assignment);
        return assignmentMapper.toAssignmentResponse(assignment);
    }

    @PreAuthorize("@courseSecurityService.hasAccessToAssignment(#id, authentication.name)")
    public AssignmentResponse getAssignmentById(UUID id) {
        Assignment assignment = assignmentRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.ASSIGNMENT_NOT_EXIST));
        if (assignment.isDeleted()) throw new AppException(ErrorCode.ASSIGNMENT_NOT_EXIST);
        return assignmentMapper.toAssignmentResponse(assignment);
    }

    public List<AssignmentResponse> getAllAssignments() {
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();
        User user = userRepository.findByUsername(name).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        Optional<Role> studentRole = roleRepository.findById("STUDENT");
        Optional<Role> teacherRole = roleRepository.findById("TEACHER");
        Optional<Role> adminRole = roleRepository.findById("ADMIN");

        List<Course> courses = new ArrayList<>();
        Set<Assignment> assignmentSet = new HashSet<>();

        if (studentRole.isPresent() && user.getRoles().contains(studentRole.get())) {
            courses = courseRepository.findAllByIsDeletedAndStudentContains(false, user);
        } else if (teacherRole.isPresent() && user.getRoles().contains(teacherRole.get())) {
            courses = courseRepository.findAllByIsDeletedAndTeacher(false, user);
        } else if (adminRole.isPresent() && user.getRoles().contains(adminRole.get())) {
            courses = courseRepository.findAllByIsDeleted(false);
        }
        var assignments = assignmentRepository.findAll();
        for (var assignment:assignments
        ) {
            if(courses.contains(assignment.getCourse())
                    && !assignment.isDeleted()
                    && assignment.getDueDate().isAfter(LocalDateTime.now()))
            assignmentSet.add(assignment);
        }
        return assignmentSet.stream()
                .map(assignmentMapper::toAssignmentResponse)
                .collect(Collectors.toList());
    }


    @PreAuthorize("@courseSecurityService.isAuthorOfCourse(#id, authentication.name)")
    public AssignmentResponse updateAssignment(UUID id, AssignmentRequest request) {
        Assignment assignment = assignmentRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.ASSIGNMENT_NOT_EXIST));

        assignmentMapper.updateAssignmentFromRequest(request, assignment); // Custom method in Mapper
        assignmentRepository.save(assignment);
        return assignmentMapper.toAssignmentResponse(assignment);
    }

    @PreAuthorize("@assignmentRepository.findById(#id).get().username == authentication.name")
    public void deleteAssignment(UUID id) {
        Assignment assignment = assignmentRepository.findById(id).orElseThrow(
                () -> new AppException(ErrorCode.ASSIGNMENT_NOT_EXIST));
        assignment.setDeleted(true);
        assignmentRepository.save(assignment);
    }
    @PreAuthorize("@courseSecurityService.hasAccessToCourse(#courseId, authentication.name)")
    public List<AssignmentResponse> getAssignmentsByCourseId(UUID courseId) {
        return assignmentRepository.findByCourseId(courseId).stream()
                .map(assignmentMapper::toAssignmentResponse)
                .collect(Collectors.toList());
    }
//
//    public List<AssignmentResponse> getAssignmentsByUsername(String username) {
//        return assignmentRepository.findByUsername(username).stream()
//                .map(assignmentMapper::toAssignmentResponse)
//                .collect(Collectors.toList());
//    }

}

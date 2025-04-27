package com.example.start.service;

import com.example.start.dto.response.StudentSubmissionsResponse;
import com.example.start.dto.response.SubmissionResponse;
import com.example.start.dto.response.user.UserResponse;
import com.example.start.entity.Assignment;
import com.example.start.entity.Submission;
import com.example.start.entity.User;
import com.example.start.exception.AppException;
import com.example.start.exception.ErrorCode;
import com.example.start.mapper.SubmissionMapper;
import com.example.start.mapper.UserMapper;
import com.example.start.repository.AssignmentRepository;
import com.example.start.repository.SubmissionRepository;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SubmissionService {
    AssignmentRepository assignmentRepository;
    SubmissionMapper submissionMapper;
    SubmissionRepository submissionRepository;
    UserService userService;
    UserMapper userMapper;
    CourseService courseService;
    CloudinaryService cloudinaryService;

    public SubmissionResponse create(UUID assignmentId, List<MultipartFile> files) throws IOException {
        Assignment assignment = assignmentRepository.findById(assignmentId).orElseThrow(
                () -> new AppException(ErrorCode.ASSIGNMENT_NOT_EXIST));
        if (LocalDateTime.now().isAfter(assignment.getDueDate())) throw new AppException(ErrorCode.MISS_THE_DEADLINE);

        Submission submission = new Submission();
        List<String> fileUrls = new ArrayList<>();
        for (var file : files
             ) { fileUrls.add(cloudinaryService.uploadFile(file));
        }
        submission.setSubmissionUrl(fileUrls);
        submission.setAssignment(assignment);
        submission.setStudent(userService.getMySelf());
        submissionRepository.save(submission);
        return submissionMapper.toSubmissionResponse(submission);
    }
    @PreAuthorize("hasAuthority('AllPermissionForTeacher')")
    public List<StudentSubmissionsResponse> getAllSubmission(UUID assignmentId){
        return submissionRepository.findAllByAssignmentId(assignmentId)
                .stream()
                .map(submissionMapper::toStudentSubmissionsResponse)
                .collect(Collectors.toList());
    }

    //Lấy danh sách sinh viên chưa nộp bài
    @PreAuthorize("hasAuthority('AllPermissionForTeacher')")
    public List<UserResponse> getAllStudentWithoutSubmission(UUID assignmentId){
        List<User> userWithSubmission = new ArrayList();
        getAllSubmission(assignmentId).stream().forEach(submissionResponse -> {
            userWithSubmission.add(submissionResponse.getStudent());
        });
        UUID courseId = assignmentRepository.findById(assignmentId)
                .orElseThrow(() -> new AppException(ErrorCode.ASSIGNMENT_NOT_EXIST))
                .getCourse().getId();
        List<User> userWithoutSubmission = courseService.getStudentList(courseId);
        userWithoutSubmission.removeAll(userWithSubmission);
        return  userWithoutSubmission.stream().map(userMapper::toUserResponse).collect(Collectors.toList());
    }

    public SubmissionResponse getSubmissionOfStudent(UUID assignmentId, UUID studentId){
        return submissionMapper.toSubmissionResponse(
                submissionRepository.findAllByAssignmentIdAndStudentId(assignmentId, studentId));
    }

    @Transactional
    @PreAuthorize("hasAuthority('AllPermissionForStudent')")
    @PostAuthorize("returnObject.getUsername() == authentication.name")
    public void deleteSubmissionOfStudent(UUID assignmentId, UUID studentId){
        submissionRepository.deleteAllByAssignmentIdAndStudentId(assignmentId, studentId);
    }

//    public SubmissionResponse editSubmissionOfStudent(UUID assignmentId, UUID studentId){
//
//        return submissionMapper.toSubmissionResponse(
//                submissionRepository.findAllByAssignmentIdAndStudentId(assignmentId, studentId));
//    }
}

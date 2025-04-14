package com.example.start.service;

import com.example.start.dto.response.SubmissionResponse;
import com.example.start.entity.Submission;
import com.example.start.exception.AppException;
import com.example.start.exception.ErrorCode;
import com.example.start.mapper.SubmissionMapper;
import com.example.start.repository.AssignmentRepository;
import com.example.start.repository.SubmissionRepository;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
    CloudinaryService cloudinaryService;

    public SubmissionResponse create(UUID assignmentId, List<MultipartFile> files) throws IOException {
        Submission submission = new Submission();
        List<String> fileUrls = new ArrayList<>();
        for (var file : files
             ) { fileUrls.add(cloudinaryService.uploadFile(file));
        }
        submission.setSubmissionUrl(fileUrls);
        submission.setAssignment(assignmentRepository.findById(assignmentId).orElseThrow(
                () -> new AppException(ErrorCode.ASSIGNMENT_NOT_EXIST)));
        submission.setStudent(userService.getMySelf());
        submissionRepository.save(submission);
        return submissionMapper.toSubmissionResponse(submission);
    }
    public List<SubmissionResponse> getAllSubmission(UUID assignmentId){
        return submissionRepository.findAllByAssignmentId(assignmentId)
                .stream()
                .map(submissionMapper::toSubmissionResponse)
                .collect(Collectors.toList());
    }
    public SubmissionResponse getSubmissionOfStudent(UUID assignmentId, UUID studentId){
        return submissionMapper.toSubmissionResponse(
                submissionRepository.findAllByAssignmentIdAndStudentId(assignmentId, studentId));
    }
}

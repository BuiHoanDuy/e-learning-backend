package com.example.start.mapper;

import com.example.start.dto.request.SubmissionRequest;
import com.example.start.dto.response.StudentSubmissionsResponse;
import com.example.start.dto.response.SubmissionResponse;
import com.example.start.entity.Submission;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SubmissionMapper {
    Submission toSubmission(SubmissionRequest submissionRequest);
    SubmissionResponse toSubmissionResponse(Submission submission);
    StudentSubmissionsResponse toStudentSubmissionsResponse(Submission submission);
}

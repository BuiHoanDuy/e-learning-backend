package com.example.start.repository;

import com.example.start.entity.Submission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface SubmissionRepository extends JpaRepository<Submission, UUID> {
    List<Submission> findAllByAssignmentId(UUID assignmentId);
    Submission findAllByAssignmentIdAndStudentId(UUID assignmentId, UUID studentId);
    void deleteAllByAssignmentIdAndStudentId(UUID assignmentId, UUID studentId);
}

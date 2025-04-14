package com.example.start.repository;

import com.example.start.entity.Assignment;
import com.example.start.entity.Course;
import com.example.start.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface AssignmentRepository extends JpaRepository<Assignment, UUID> {
    List<Assignment> findByCourseId(UUID courseId);
//    List<Assignment> findByUsername(String username);
}

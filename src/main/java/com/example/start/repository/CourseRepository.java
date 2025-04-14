package com.example.start.repository;

import com.example.start.entity.Course;
import com.example.start.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CourseRepository extends JpaRepository<Course, UUID> {
    List<Course> findAllByIsDeleted(boolean isDeleted);
    List<Course> findAllByIsDeletedAndTeacher(boolean isDeleted, User teacher);
    List<Course> findAllByIsDeletedAndStudentContains(boolean isDeleted, User student);
}
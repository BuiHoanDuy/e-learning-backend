package com.example.start.repository;

import com.example.start.entity.Course;
import com.example.start.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CourseRepository extends JpaRepository<Course, UUID> {
    List<Course> findAllByIsDeleted(boolean isDeleted);
    List<Course> findAllByIsDeletedAndTeacher(boolean isDeleted, User teacher);
    List<Course> findAllByIsDeletedAndStudentContains(boolean isDeleted, User student);
    Optional<Course> findByIsDeletedAndJoinCode(boolean isDeleted, String joinCode);

    @Query(value = "SELECT u.* FROM users u JOIN courses_student cs ON " +
            "u.id = cs.student_id WHERE cs.course_id = :courseId", nativeQuery = true)
    List<User> findAllUserByCourseId(@Param("courseId") UUID courseId);
}
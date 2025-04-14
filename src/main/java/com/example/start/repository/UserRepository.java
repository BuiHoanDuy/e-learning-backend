package com.example.start.repository;
import com.example.start.entity.Role;
import com.example.start.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    @Query("SELECT u FROM User u WHERE u.fullName LIKE %:name% ")
    List<User> findByFullname(String name);
    Optional<User> findById(String id);
    boolean existsByUsername(String name);
    Optional<User> findByUsername(String username);
}

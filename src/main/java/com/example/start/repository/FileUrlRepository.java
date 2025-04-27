package com.example.start.repository;

import com.example.start.entity.FileUrl;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface FileUrlRepository extends JpaRepository<FileUrl, UUID> {

}

package com.example.start.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Table(name = "Submissions")
public class Submission {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "assignmentId", nullable = false)
    private Assignment assignment;

    @ManyToOne
    @JoinColumn(name = "studentId", nullable = false)
    private User student;

    @Builder.Default
    @ElementCollection
    @CollectionTable(name = "submission_urls", joinColumns = @JoinColumn(name = "submission_id"))
    @Column(name = "url", length = 500)
    private List<String> submissionUrl = new ArrayList<>();

    @Column(nullable = false)
    private LocalDateTime submittedAt;

    @PrePersist
    void prePersist(){
        submittedAt = LocalDateTime.now();
        if (submissionUrl == null) {
            submissionUrl = new ArrayList<>();
        }
    }
}

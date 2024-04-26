package com.example.lmstaskservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tasks")
public class Task {

    @Id
    private UUID id;

    private String title;

    private String description;

    private String imageUrl;

    private UUID assignedUserId;

    //initialized it as well otherwise getting null in response instead of []
    private List<String> tags = new ArrayList<>();               //tech stack of assignment

    private LocalDateTime deadline;

    private LocalDateTime createdAt;

    private TaskStatus status;

}

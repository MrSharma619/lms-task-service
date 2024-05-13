package com.example.lmstaskservice.repository;

import com.example.lmstaskservice.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TaskRepository extends JpaRepository<Task, UUID> {

    //need to use custom query here
    @Query(value = "SELECT * FROM task WHERE :userId = ANY (assigned_user_id)", nativeQuery = true)
    public List<Task> findByAssignedUserId(UUID userId);

    public List<Task> findByCreatedByUserId(UUID userId);

}

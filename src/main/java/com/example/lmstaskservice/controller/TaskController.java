package com.example.lmstaskservice.controller;

import com.example.lmstaskservice.dto.UserDto;
import com.example.lmstaskservice.entity.Task;
import com.example.lmstaskservice.entity.TaskStatus;
import com.example.lmstaskservice.service.TaskService;
import com.example.lmstaskservice.specifications.UserManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.service.annotation.GetExchange;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/task")
public class TaskController {

    @Autowired
    private TaskService service;

    @Autowired
    private UserManager userManager;

    @PostMapping
    public ResponseEntity<Task> createTask(@RequestBody Task task,
                                           @RequestHeader("Authorization") String token) throws Exception {

        UserDto user = userManager.getUserProfile(token);

        Task createdTask = service.createTask(task, user.getRole());

        return new ResponseEntity<>(createdTask, HttpStatus.CREATED);

    }

    @GetMapping("/{taskId}")
    public ResponseEntity<Task> getTaskById(@PathVariable UUID taskId) throws Exception {
        Task task = service.getTaskById(taskId);

        return new ResponseEntity<>(task, HttpStatus.OK);

    }

    @GetMapping("/user")
    public ResponseEntity<List<Task>> getTasksAssignedToUser(
            @RequestParam(required = false) TaskStatus status,
            @RequestHeader("Authorization") String token
            ){

        UserDto user = userManager.getUserProfile(token);

        List<Task> tasks = service.getTasksAssignedToUser(user.getId(), status);

        return new ResponseEntity<>(tasks, HttpStatus.OK);

    }

    @GetMapping
    public ResponseEntity<List<Task>> getAllTasks(@RequestParam(required = false) TaskStatus status){
        List<Task> tasks = service.getAllTasks(status);

        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }

    @PatchMapping("/{taskId}/user/{userId}/assign")
    public ResponseEntity<Task> assignToUser(
            @PathVariable UUID taskId,
            @PathVariable UUID userId
    ) throws Exception {
        Task task = service.assignToUser(taskId, userId);

        return new ResponseEntity<>(task, HttpStatus.OK);
    }

    @PatchMapping("/{taskId}")
    public ResponseEntity<Task> updateTask(
            @PathVariable UUID taskId,
            @RequestBody Task updatedTask,
            @RequestHeader("Authorization") String token
    ) throws Exception {

        UserDto user = userManager.getUserProfile(token);

        Task task = service.updateTask(
                taskId,
                updatedTask,
                user.getId()
                );

        return new ResponseEntity<>(task, HttpStatus.OK);

    }

    @PatchMapping("/{taskId}/complete")
    public ResponseEntity<Task> completeTask(@PathVariable UUID taskId) throws Exception {

        Task task = service.completeTask(taskId);

        return new ResponseEntity<>(task, HttpStatus.OK);

    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity<Void> deleteTask(@PathVariable UUID taskId) throws Exception {

        service.deleteTask(taskId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }

}

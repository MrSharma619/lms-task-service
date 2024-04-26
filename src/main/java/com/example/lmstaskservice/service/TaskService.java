package com.example.lmstaskservice.service;

import com.example.lmstaskservice.entity.Task;
import com.example.lmstaskservice.entity.TaskStatus;
import com.example.lmstaskservice.repository.TaskRepository;
import com.example.lmstaskservice.specifications.TaskManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class TaskService implements TaskManager {

    @Autowired
    private TaskRepository repository;

    @Override
    public Task createTask(Task task, String requesterRole) throws Exception {

        if (!requesterRole.equals("ROLE_TEACHER")){
            throw new Exception("Only teachers can create tasks...");
        }

        task.setId(UUID.randomUUID());
        task.setCreatedAt(LocalDateTime.now());
        task.setStatus(TaskStatus.PENDING);

        return repository.save(task);
    }

    @Override
    public Task getTaskById(UUID taskId) throws Exception {
        return repository.findById(taskId).orElseThrow(() -> new Exception("Task not found for id: " + taskId));
    }

    @Override
    public List<Task> getAllTasks(TaskStatus status) {

        List<Task> allTasks = repository.findAll();

        List<Task> filteredTasks = allTasks
                .stream()
                .filter(
                        task -> status == null || task.getStatus().name().equalsIgnoreCase(status.toString())
                )
                .collect(Collectors.toList());

        return filteredTasks;
    }

    @Override
    public Task updateTask(UUID taskId, Task updatedTask, UUID userId) throws Exception {

        Task existingTask = getTaskById(taskId);

        if(updatedTask.getTitle() != null){
            existingTask.setTitle(updatedTask.getTitle());
        }

        if(updatedTask.getDescription() != null){
            existingTask.setDescription(updatedTask.getDescription());
        }

        if(updatedTask.getImageUrl() != null){
            existingTask.setImageUrl(updatedTask.getImageUrl());
        }

        if(updatedTask.getStatus() != null){
            existingTask.setStatus(updatedTask.getStatus());
        }

        if(updatedTask.getDeadline() != null){
            existingTask.setDeadline(updatedTask.getDeadline());
        }

        return repository.save(existingTask);

    }

    @Override
    public void deleteTask(UUID taskId) throws Exception {

        //if not found, exception will be thrown in this method call
        getTaskById(taskId);

        repository.deleteById(taskId);
    }

    @Override
    public Task assignToUser(UUID taskId, UUID userId) throws Exception {
        Task existingTask = getTaskById(taskId);

        existingTask.setAssignedUserId(userId);
        existingTask.setStatus(TaskStatus.ASSIGNED);

        return repository.save(existingTask);
    }

    @Override
    public List<Task> getTasksAssignedToUser(UUID userId, TaskStatus status) {

        List<Task> allTasksAssignedToUser = repository.findByAssignedUserId(userId);

        List<Task> filteredTasks = allTasksAssignedToUser
                .stream()
                .filter(
                        task -> status == null || task.getStatus().name().equalsIgnoreCase(status.toString())
                )
                .collect(Collectors.toList());

        return filteredTasks;
    }

    @Override
    public Task completeTask(UUID taskId) throws Exception {

        Task existingTask = getTaskById(taskId);

        existingTask.setStatus(TaskStatus.DONE);

        return repository.save(existingTask);

    }

}

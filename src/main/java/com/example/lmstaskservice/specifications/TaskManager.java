package com.example.lmstaskservice.specifications;

import com.example.lmstaskservice.entity.Task;
import com.example.lmstaskservice.entity.TaskStatus;

import java.util.List;
import java.util.UUID;

//call the package specifications
//and call interfaces managers
// and implementations as service
public interface TaskManager {

    Task createTask(Task task, String requesterRole, UUID createdByUserId) throws Exception;

    Task getTaskById(UUID taskId) throws Exception;

    List<Task> getAllTasks(TaskStatus status);

    Task updateTask(UUID taskId, Task updatedTask, UUID userId) throws Exception;

    void deleteTask(UUID taskId) throws Exception;

    Task assignToUser(UUID taskId, UUID userId) throws Exception;

    List<Task> getTasksAssignedToUser(UUID userId, TaskStatus status);

    List<Task> getTasksCreatedByUser(UUID userId, TaskStatus status);

    Task completeTask(UUID taskId) throws Exception;

}

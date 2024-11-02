package com.example.tasko.service;

import com.example.tasko.model.Enterprise;
import com.example.tasko.model.Task;
import com.example.tasko.model.User;
import com.example.tasko.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public Task getTaskById(Long id) {
        return taskRepository.findById(id).orElse(null);
    }

    @Transactional
    public Task createTask(Task task) {
        return taskRepository.save(task);
    }

    @Transactional
    public Task updateTask(Task task) {
        return taskRepository.save(task);
    }

    @Transactional
    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }

    public Page<Task> getTasksPaginated(PageRequest pageRequest, String sort, String filter) {
        return taskRepository.findAll(pageRequest);
    }

    public List<Task> getTasksByUser(User user) {
        return taskRepository.findByAssignedUsersContaining(user);
    }

    public long getCompletedTasksCount() {
        return taskRepository.countByCompleted(true);
    }

    public long getPendingTasksCount() {
        return taskRepository.countByCompleted(false);
    }

    public long countActiveTasksByEnterprise(Enterprise enterprise) {
        return taskRepository.countByEnterpriseAndCompletedFalse(enterprise);
    }

    public List<Task> getRecentTasksByEnterprise(Enterprise enterprise) {
        return taskRepository.findTop5ByEnterpriseOrderByDueDateDesc(enterprise);
    }

    public List<Task> getRecentTasksByUser(User user) {
        return taskRepository.findTop5ByAssignedUsersContainingOrderByDueDateAsc(user);
    }

    @Transactional
    public Task completeTask(Long id) {
        Task task = taskRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Task not found with id: " + id));
        task.setCompleted(true);
        return taskRepository.save(task);
    }
}
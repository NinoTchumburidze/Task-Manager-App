package com.example.Application.service;

import com.example.Application.model.Task;
import com.example.Application.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    public Task createTask(String taskDesc, String user, LocalDate taskDate, boolean taskState) {
        Task task = new Task();
        task.setTaskDesc(taskDesc);
        task.setUser(user);
        task.setTaskDate(taskDate);
        task.setTaskState(false);

        return taskRepository.save(task);
    }

    public Task getTaskById(Long taskId) {
        return taskRepository.findById(taskId).orElse(null);  // Get the task by ID
    }

    public void save(Task task) {
        taskRepository.save(task);  // Save or update the task in the repository
    }
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public List<Task> getTasksForToday() {
        LocalDate today = LocalDate.now();
        return taskRepository.findByTaskDate(today);
    }

    public List<Task> getTasksByUsername(String username) {
        return taskRepository.findByUser(username);
    }

    public List<Task> getTasksByUsernameAndDate(String username, LocalDate date) {
        System.out.println("Fetching tasks for " + username + " on " + date);

        return getTasksByUsername(username).stream()
                .filter(task -> task.getTaskDate().equals(date))
                .collect(Collectors.toList());
    }
}

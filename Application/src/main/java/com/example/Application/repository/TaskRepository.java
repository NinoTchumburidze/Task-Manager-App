package com.example.Application.repository;

import com.example.Application.model.Task;
import com.example.Application.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import com.example.Application.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;


public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByTaskDate(LocalDate taskDate);

    List<Task> findByUser(String username);
}
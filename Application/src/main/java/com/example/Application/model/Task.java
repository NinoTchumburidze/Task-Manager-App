package com.example.Application.model;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // Auto-increment ID
    private Long id;

    @Column(nullable = false)
    private String taskDesc;

    @Column(nullable = false)
    private String user;

    @Column(nullable = false)
    private LocalDate taskDate;

    @Column(nullable = false)
    private boolean taskState;

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTaskDesc() {
        return taskDesc;
    }

    public void setTaskDesc(String taskDesc) {
        this.taskDesc = taskDesc;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public LocalDate getTaskDate() {
        return taskDate;
    }

    public void setTaskDate(LocalDate taskDate) {
        this.taskDate = taskDate;
    }
    public boolean getTaskState() {
        return taskState;
    }

    public void setTaskState(boolean taskState) {
        this.taskState = taskState;
    }
}

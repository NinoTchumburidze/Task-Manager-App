package com.example.Application.controller;

import com.example.Application.model.Task;
import com.example.Application.service.TaskService;
import com.example.Application.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.ui.Model;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

@Controller
public class HomeController {

    @Autowired
    private UserService userService;
    @Autowired
    private TaskService taskService;


    @GetMapping("/")
    public String home() {
       return "LoginPage";
    }

    // Serve the CreateNew page
    @GetMapping("/createNew")
    public String createNew() {
        return "createNew";
    }

    @GetMapping("/HomePage")
    public String homePage(HttpServletRequest request, Model model) {
        String username = (String) request.getSession().getAttribute("username");

        if (username == null) {
            return "redirect:/login";
        }

        List<Task> todayTasks = taskService.getTasksByUsernameAndDate(username, LocalDate.now());
        model.addAttribute("todayTasks", todayTasks);

        model.addAttribute("username", username);
        return "HomePage";  // The homepage view
    }

    @GetMapping("/newTask")
    public String newTask(Model model) {
        // Logic to show the task creation page
        return "newTask";
    }
    @PostMapping("/logout")
    public String logout(HttpServletRequest httpServletRequest) {
        // Invalidate the session to log out the user
        httpServletRequest.getSession().invalidate();
        return "redirect:/";
    }
    @PostMapping("/tasks/updateTaskStatus")
    @ResponseBody
    public ResponseEntity<?> updateTaskStatus(@RequestParam Long taskId, @RequestParam boolean taskState) {
        try {
            System.out.println("Received taskId: " + taskId + " taskState: " + taskState);
            Task task = taskService.getTaskById(taskId);
            if (task == null) {
                return ResponseEntity.badRequest().body("Task not found");
            }

            task.setTaskState(taskState);
            taskService.save(task);
            return ResponseEntity.ok(task);  // JSON response
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Internal Server Error: " + e.getMessage());
        }
    }


    @GetMapping("/allTasks")
    public String allTasks(Model model, HttpServletRequest httpServletRequest) {
        String username = (String) httpServletRequest.getSession().getAttribute("username");

        if (username == null) {
            return "redirect:/login";
        }

        List<Task> userTasks = taskService.getTasksByUsername(username); // ONLY this user's tasks

        // Group tasks by date (newest to oldest)
        Map<LocalDate, List<Task>> groupedByDate = userTasks.stream()
                .collect(Collectors.groupingBy(
                        Task::getTaskDate,
                        () -> new TreeMap<>(Comparator.reverseOrder()),
                        Collectors.toList()
                ));

        model.addAttribute("groupedTasks", groupedByDate);
        return "allTasks";
    }
    // Handle the login POST request
    @PostMapping("/login")
    public String login(@RequestParam("username") String username,
                        @RequestParam("password") String password, HttpServletRequest httpServletRequest, Model model) {
        if (userService.authenticateUser(username, password)) {
            httpServletRequest.getSession().setAttribute("username", username);
            model.addAttribute("username", username);
            List<Task> todayTasks = taskService.getTasksByUsernameAndDate(username, LocalDate.now());
            model.addAttribute("todayTasks", todayTasks);

            return "HomePage";// Redirect to welcome page if authentication is successful
        } else {
            return "LoginPage";  // Stay on the login page if authentication fails
        }
    }



    @GetMapping("/home")
    public String home(HttpServletRequest httpServletRequest, Model model) {
        // Get the username from the session
        String username = (String) httpServletRequest.getSession().getAttribute("username");
        List<Task> todayTasks = taskService.getTasksByUsernameAndDate(username, LocalDate.now());
        model.addAttribute("todayTasks", todayTasks);
        System.out.println("Today’s tasks for " + username + ": " + todayTasks.size() + " tasks found.");
        if (username == null) {
            // If the session does not contain a username, redirect to login page

            return "redirect:/login";
        }
        httpServletRequest.getSession().setAttribute("username", username);
        model.addAttribute("username", username);


        return "HomePage"; // Return the homepage template
    }


    @PostMapping("/register")
    public String register(@RequestParam("username") String username,
                           @RequestParam("password") String password,
                           @RequestParam("confirmPassword") String confirmPassword,
                           Model model) {
        if (!password.equals(confirmPassword)) {
            model.addAttribute("error", "Passwords do not match.");
            return "createNew";
        }
        if (!userService.registerUser(username, password)) {
            model.addAttribute("error", "Username already exists.");
            return "createNew";
        }
        return "redirect:/?registrationSuccess=true";    }




    @PostMapping("/submitTask")
    public String submitTask(@RequestParam("taskDescription") String taskDescription,
                             @RequestParam("taskDate") String taskDate,
                             HttpServletRequest request) {
        // Logic to handle the task submission (save to database)
        String username = (String) request.getSession().getAttribute("username");

        if (username != null) {
            // Save the task with the description, date, and username
            taskService.createTask(taskDescription,  username, LocalDate.parse(taskDate), false);
            return "redirect:/HomePage";  // Redirect to homepage after task creation
        }

        return "redirect:/login";  // Redirect to login page if not authenticated
    }

}

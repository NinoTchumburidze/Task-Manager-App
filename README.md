# Task Management Web App

This is a simple Task Management web application built using **Spring Boot** and **Thymeleaf**. It allows users to register, log in, create tasks for specific dates, mark tasks as complete, and view all their tasks grouped by date.

## ✨ Features

- ✅ User Registration and Login
- 🗓️ Task Creation with Description and Date
- ✅ Mark Tasks as Complete or Incomplete
- 📆 View Today’s Tasks or All Tasks Grouped by Date
- 🚪 Secure Logout Functionality with Session Handling
- 🎨 Beautiful UI with custom CSS and glassmorphism effect

## 🛠️ Technologies Used

- Java
- Spring Boot
- Thymeleaf
- HTML, CSS, JavaScript
- Maven
- Jakarta Servlet API

## 🔧 How It Works

1. **Landing Page**  
   Users are first redirected to the login page (`/`).

2. **Login / Register**  
   Users can log in or create a new account.

3. **HomePage**  
   Authenticated users are taken to the homepage, where they can:
   - View tasks for today
   - Mark tasks as complete
   - Access all tasks
   - Create new tasks

4. **Session Management**  
   The session tracks logged-in users and displays content based on session attributes.

5. **Logout**  
   A styled logout button allows users to safely end their

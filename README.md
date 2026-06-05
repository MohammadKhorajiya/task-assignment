  Task Assignment System

A robust, production-ready Task Management API built with Spring Boot and MongoDB, featuring secure authentication, role-based access control (RBAC), and dynamic configuration caching.

🚀 Features
Secure Authentication: JWT-based login and signup with secure password hashing (BCrypt).

Role-Based Access Control (RBAC): Separate access levels for USER and ADMIN.

Dynamic Configuration: Efficient caching system using ConcurrentHashMap to manage application settings (like task limits) on the fly without restarting.

Advanced Task Management: CRUD operations with status filtering, priority categorization, and pagination.

Async Notifications: Automated email notifications for task creation using JavaMailSender and @Async.

Production-Ready: Global exception handling, robust logging, and Actuator health monitoring.

🛠 Tech Stack
Backend: Java 21, Spring Boot 3.2.6

Security: Spring Security, JSON Web Tokens (JWT)

Database: MongoDB (via Spring Data MongoDB)

Deployment: Render

Testing/API Management: EchoAPI

⚙️ Environment Variables
To run this application, you must configure the following environment variables:

MONGO_URI: Your MongoDB connection string.

JWT_SECRET: A secure key for signing JWT tokens.

SPRING_MAIL_HOST, SPRING_MAIL_PORT, SPRING_MAIL_USERNAME, SPRING_MAIL_PASSWORD: SMTP credentials for email services.

🚀 Deployment
This project is deployed on Render.

Base URL: https://task-assignment-mgvz.onrender.com

Health Check: GET /public/health

👨‍💻 Admin Control
Admins can manage the application settings dynamically via:

POST /admin/refresh-cache: Refreshes the in-memory configuration cache from the MongoDB database.

Developed by,
MohammadKhoarajiya

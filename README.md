# Task Assignment System (REST API)

A production-ready Backend application built with **Spring Boot**, designed to help users manage their tasks efficiently while providing administrators powerful management tools.

## 🚀 Core Functionalities

### 🔐 Security & Authentication
* **JWT (JSON Web Token):** Secure authentication system. No one can access the data without a valid token.
* **RBAC (Role-Based Access Control):** * **USER:** Can manage their own tasks.
    * **ADMIN:** Can view all users, manage the system, and promote users to Admin status.

### 📝 Task Features
* **Full CRUD Operations:** Users can Create, Read, Update, and Delete their tasks.
* **Advanced Search & Filtering:** Filter tasks by status and priority with pagination support.
* **Email Notifications:** Asynchronous email system to notify users when a task is created.

### ⚙️ Performance & Database
* **Database (MongoDB Atlas):** Using a cloud-based NoSQL database for flexible data storage.
* **Dynamic Caching:** Integrated in-memory caching system to manage application settings (like task limits) dynamically without restarting the server.

### 🛠 Admin Powers
* **User Management:** Admins can monitor all registered users in the system.
* **Promote to Admin:** Specialized endpoint to upgrade existing users to Admin status without losing data.
* **Dynamic Config Refresh:** Admin-only endpoint to refresh app settings from the database instantly.

---

## 🛠 Tech Stack
* **Language:** Java 21
* **Framework:** Spring Boot 3.2.6
* **Security:** Spring Security & JWT
* **Database:** MongoDB (Cloud Atlas)
* **Cloud Deployment:** Render
* **Testing/API Management:** EchoAPI

---
*Developed by Mohammad Khorajiya*

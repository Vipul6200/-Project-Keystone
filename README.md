Keystone – Work Order Management System :
 Keystone is a Java Full Stack Work Order Management System developed as part of the Zidio Development internship project. It helps manage work orders, customers, sites, technicians, work progress, and reports through a web-based application.

Project Overview:
 The application provides a centralized platform to create and manage work orders, assign technicians, track work-order status, record parts and time, and monitor service performance.

Technology Stack:
 Backend: 
 > Java 21
 > Spring Boot 4.1.1
 > Spring Data JPA
 > Spring Security
 > JWT Authentication
 > PostgreSQL
 > Flyway Database Migration
 > Maven
 > Swagger / OpenAPI

 Frontend:
 > React 19
 > TypeScript
 > Vite

Key Features:
 > User authentication and role-based access.
 > Work order creation and management.
 > Customer and site management.
 > Technician work-order assignment and field views.
 > Work-order status tracking and history.
 > Parts and time tracking.
 > SLA and overdue work-order monitoring.
 > Dashboard and work-order reports.
 > API documentation through Swagger UI.

Project Structure:
keystone/
├── frontend/
│   ├── src/
│   │   ├── pages/
│   │   ├── App.tsx
│   │   └── main.tsx
│   ├── package.json
│   └── vite.config.ts
├── src/
│   ├── main/
│   │   ├── java/
│   │   └── resources/
│   │       └── db/migration/
│   └── test/
├── pom.xml
├── mvnw
├── mvnw.cmd
└── README.md

Prerequisites: 
 Install the following before running the project:
 > JDK 21
 > PostgreSQL
 > Node.js and npm
 > Git

Database Setup:
 1. Open PostgreSQL.
 2. Create a database named "keystone".
 3. Configure the database connection using environment variables:

SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/keystone
SPRING_DATASOURCE_USERNAME=your_postgres_username
SPRING_DATASOURCE_PASSWORD=your_postgres_password

 Replace the example username and password with your own local PostgreSQL credentials.
 Flyway automatically manages database schema migrations when the backend starts. Migration scripts are located in:

src/main/resources/db/migration/

 The project includes migration scripts V1 through V13. No sample login users are inserted by these migration scripts; valid user accounts must be configured separately.

Backend Setup and Run

 Open a terminal in the project root directory.

Build the project:

 .\mvnw.cmd clean package

Run the Spring Boot application:

 .\mvnw.cmd spring-boot:run

The backend runs at:

 https://project-keystone-2.onrender.com

Frontend Setup and Run

Open another terminal:
 cd frontend
 npm install
 npm run dev

Open the local frontend URL displayed by Vite in the terminal.

To build the frontend:

 npm run build

API Documentation

Swagger UI:
 https://project-keystone-2.onrender.com/swagger-ui/index.html

OpenAPI documentation:
 https://project-keystone-2.onrender.com/v3/api-docs

Testing: 
 Run backend tests from the project root:

 .\mvnw.cmd test

Build the frontend:

 cd frontend
 npm run build

Security

 The backend uses Spring Security and JWT-based authentication. Role-based authorization is configured for protected API endpoints.

 Keep database credentials and other local secrets private. Do not commit passwords, tokens, or private configuration files to GitHub.

Repository

 GitHub: https://github.com/Vipul6200/Project-Keystone

Internship Project
 Developed as part of the Java Full Stack Internship at Zidio Development.
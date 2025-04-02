# Student Management System

A Spring Boot REST API application for managing students and users.

## Features

- CRUD operations for students
- RESTful API endpoints
- Swagger UI documentation
- Input validation
- DTO pattern implementation
- Bootstrap-styled web interface

## Prerequisites

- Java 17 or higher
- Maven 3.6 or higher

## Building the Application

1. Clone the repository:
```bash
git clone <repository-url>
```

2. Navigate to the project directory:
```bash
cd student-management
```

3. Build the project:
```bash
mvn clean install
```

## Running the Application

1. Run the application:
```bash
mvn spring:boot run
```

2. Access the application:
- Web Interface: http://localhost:8080/home
- Swagger UI: http://localhost:8080/swagger-ui.html
- API Documentation: http://localhost:8080/api-docs

## API Endpoints

- GET /api/students - Get all students
- GET /api/students/{id} - Get student by ID
- POST /api/students - Create new student
- PUT /api/students/{id} - Update student
- DELETE /api/students/{id} - Delete student

## Technologies Used

- Spring Boot 3.2.3
- Spring Web
- Spring Validation
- Lombok
- Springdoc OpenAPI (Swagger)
- Thymeleaf
- Bootstrap 5.3.0 
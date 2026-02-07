# Student Management System

A comprehensive REST API for managing students, teachers, and courses built with Spring Boot 3.5.9, secured with JWT authentication.

## 📋 Table of Contents

- [Features](#features)
- [Technologies](#technologies)
- [Prerequisites](#prerequisites)
- [Installation](#installation)
- [Configuration](#configuration)
- [Database Schema](#database-schema)
- [API Endpoints](#api-endpoints)
- [Authentication](#authentication)
- [Security Roles](#security-roles)
- [Usage Examples](#usage-examples)
- [Project Structure](#project-structure)

## ✨ Features

- **User Authentication & Authorization** with JWT tokens
- **Role-based Access Control** (ADMIN, HEADMASTER, LECTURER)
- **Student Management** - CRUD operations with course enrollments
- **Teacher Management** - Manage teachers with role assignments
- **Course Management** - Create and assign courses to teachers
- **Relational Data** - Students can enroll in multiple courses
- **Audit Trail** - Track created/updated by and timestamps
- **Soft Delete** - Data is marked as deleted, not removed from database

## 🛠 Technologies

- **Java 17**
- **Spring Boot 3.5.9**
- **Spring Security** with JWT Authentication
- **Spring Data JPA** for database operations
- **MySQL** database
- **Lombok** for reducing boilerplate code
- **Maven** for dependency management
- **JWT (JJWT 0.11.5)** for token generation and validation

## 📦 Prerequisites

Before running this application, make sure you have:

- Java 17 or higher
- MySQL 8.0 or higher
- Maven 3.6 or higher
- Postman or any REST client for API testing

## 🚀 Installation

1. **Clone the repository**
```bash
git clone <repository-url>
cd springboot2026
```

2. **Create MySQL Database**
```sql
CREATE DATABASE studentdb;
```

3. **Update Database Configuration**

Edit `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/studentdb
spring.datasource.username=root
spring.datasource.password=root
```

4. **Build the project**
```bash
mvn clean install
```

5. **Run the application**
```bash
mvn spring-boot:run
```

The application will start on `http://localhost:8080`

## ⚙️ Configuration

### Application Properties

```properties
# Database Configuration
spring.datasource.url=jdbc:mysql://localhost:3306/studentdb
spring.datasource.username=root
spring.datasource.password=root
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

# JPA Configuration
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.database-platform=org.hibernate.dialect.MySQLDialect

# JWT Configuration
jwt.secret=NjRieXRlc1NlY3JldEtleUZvckpXVEhTMjU2U2lnbmF0dXJlVGhpc0lzVmVyeVNlY3VyZUtleUZvclNwcmluZ0Jvb3RBcHBsaWNhdGlvbjIwMjY=
jwt.expiration=3600000
```

## 🗄 Database Schema

### Tables

**school_users**
- id (PK)
- username (unique)
- password (encrypted)
- role (ENUM: ADMIN, HEADMASTER, LECTURER)

**teachers**
- teacher_id (PK)
- name
- email
- phone_number
- address
- role (ENUM: ADMIN, HEADMASTER, LECTURER)
- status (ENUM: ACTIVE, DELETE)
- created_date, created_by
- updated_date, updated_by

**course**
- id (PK)
- name
- teacher_id (FK -> teachers)
- status (ENUM: ACTIVE, DELETE)
- created_date, created_by
- updated_date, updated_by

**student**
- id (PK)
- name
- email
- phone_number
- address
- status (ENUM: ACTIVE, DELETE)
- created_date, created_by
- updated_date, updated_by

**student_courses** (Join Table)
- student_id (FK -> student)
- course_id (FK -> course)

### Relationships

- **Teacher → Course**: One-to-Many (One teacher can teach multiple courses)
- **Student → Course**: Many-to-Many (Students can enroll in multiple courses)
- **Course → Teacher**: Many-to-One (Each course has one assigned teacher)

## 📡 API Endpoints

### Authentication

| Method | Endpoint | Description | Access |
|--------|----------|-------------|--------|
| POST | `/auth/register` | Register new user | Public |
| POST | `/auth/login` | Login and get JWT token | Public |

### Students

| Method | Endpoint | Description | Access |
|--------|----------|-------------|--------|
| POST | `/students` | Create new student | ADMIN |
| GET | `/students` | Get all students with courses and teachers | ADMIN |
| PUT | `/students/{id}` | Update student | ADMIN |
| DELETE | `/students/{id}` | Soft delete student | ADMIN |

### Teachers

| Method | Endpoint | Description | Access |
|--------|----------|-------------|--------|
| POST | `/teachers` | Create new teacher | ADMIN, HEADMASTER |
| GET | `/teachers` | Get all teachers | ADMIN, HEADMASTER |
| PUT | `/teachers/{id}` | Update teacher | ADMIN, HEADMASTER |
| DELETE | `/teachers/{id}` | Soft delete teacher | ADMIN, HEADMASTER |

### Courses

| Method | Endpoint | Description | Access |
|--------|----------|-------------|--------|
| POST | `/courses` | Create new course | ADMIN, HEADMASTER |
| GET | `/courses` | Get all courses with teacher info | ADMIN, HEADMASTER |
| GET | `/courses/teacher/{teacherId}` | Get courses by teacher | ADMIN, HEADMASTER, LECTURER |
| PUT | `/courses/{id}` | Update course | ADMIN, HEADMASTER |
| DELETE | `/courses/{id}` | Soft delete course | ADMIN, HEADMASTER |

## 🔐 Authentication

This API uses JWT (JSON Web Token) for authentication.

### How to Authenticate

1. **Register a new user** or use existing credentials
2. **Login** to get JWT token
3. **Include the token** in the Authorization header for all protected endpoints

```
Authorization: Bearer <your-jwt-token>
```

### Token Expiration

- Tokens expire after 1 hour (3600000 milliseconds)
- After expiration, you need to login again to get a new token

## 👥 Security Roles

| Role | Description | Permissions |
|------|-------------|-------------|
| **ADMIN** | Full system access | All operations on Students, Teachers, Courses |
| **HEADMASTER** | School management | Manage Teachers and Courses |
| **LECTURER** | Teacher access | View assigned courses |

### Role-Based Access Matrix

| Resource | ADMIN | HEADMASTER | LECTURER |
|----------|-------|------------|----------|
| Students | ✅ Full | ❌ | ❌ |
| Teachers | ✅ Full | ✅ Full | ❌ |
| Courses | ✅ Full | ✅ Full | ✅ View Only |

## 📝 Usage Examples

### 1. Register a User

**Request:**
```http
POST http://localhost:8080/auth/register
Content-Type: application/json

{
  "username": "admin01",
  "password": "admin01",
  "role": "ADMIN"
}
```

**Response:**
```
User registered
```

### 2. Login

**Request:**
```http
POST http://localhost:8080/auth/login
Content-Type: application/json

{
  "username": "admin01",
  "password": "admin01"
}
```

**Response:**
```
eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbjAxIiwicm9sZSI6IkFETUlOIiwiaWF0IjoxNzA...
```

### 3. Create a Teacher

**Request:**
```http
POST http://localhost:8080/teachers
Authorization: Bearer <your-jwt-token>
Content-Type: application/json

{
  "name": "John Doe",
  "email": "john.doe@school.com",
  "phoneNumber": "091234567",
  "address": "New York",
  "role": "LECTURER"
}
```

**Response:**
```json
{
  "id": 1,
  "name": "John Doe",
  "email": "john.doe@school.com",
  "phoneNumber": "091234567",
  "address": "New York",
  "role": "LECTURER",
  "status": "ACTIVE",
  "createdDate": "2026-02-07T10:30:00",
  "createdBy": "admin01"
}
```

### 4. Create a Course

**Request:**
```http
POST http://localhost:8080/courses
Authorization: Bearer <your-jwt-token>
Content-Type: application/json

{
  "courseName": "Spring Boot Advanced",
  "teacherId": 1
}
```

**Response:**
```json
{
  "id": 1,
  "courseName": "Spring Boot Advanced",
  "teacherId": 1,
  "teacher": {
    "id": 1,
    "name": "John Doe",
    "email": "john.doe@school.com",
    "phoneNumber": "091234567",
    "address": "New York",
    "role": "LECTURER",
    "status": "ACTIVE"
  },
  "status": "ACTIVE",
  "createdDate": "2026-02-07T10:35:00",
  "createdBy": "admin01"
}
```

### 5. Create a Student with Course Enrollment

**Request:**
```http
POST http://localhost:8080/students
Authorization: Bearer <your-jwt-token>
Content-Type: application/json

{
  "name": "Jane Smith",
  "email": "jane.smith@student.com",
  "phoneNumber": "091234568",
  "address": "Los Angeles",
  "courseIdList": [1, 2]
}
```

**Response:**
```json
{
  "id": 1,
  "name": "Jane Smith",
  "email": "jane.smith@student.com",
  "phoneNumber": "091234568",
  "address": "Los Angeles",
  "courseIdList": [1, 2],
  "courses": [
    {
      "id": 1,
      "courseName": "Spring Boot Advanced",
      "teacherId": 1,
      "teacher": {
        "id": 1,
        "name": "John Doe",
        "email": "john.doe@school.com",
        "role": "LECTURER"
      }
    },
    {
      "id": 2,
      "courseName": "React JS Fundamentals",
      "teacherId": 2,
      "teacher": {
        "id": 2,
        "name": "Sarah Johnson",
        "email": "sarah.j@school.com",
        "role": "LECTURER"
      }
    }
  ],
  "status": "ACTIVE",
  "createdDate": "2026-02-07T11:00:00",
  "createdBy": "admin01"
}
```

### 6. Get All Students (with nested Course and Teacher info)

**Request:**
```http
GET http://localhost:8080/students
Authorization: Bearer <your-jwt-token>
```

**Response:**
```json
[
  {
    "id": 1,
    "name": "Jane Smith",
    "email": "jane.smith@student.com",
    "phoneNumber": "091234568",
    "address": "Los Angeles",
    "courseIdList": [1, 2],
    "courses": [
      {
        "id": 1,
        "courseName": "Spring Boot Advanced",
        "teacherId": 1,
        "teacher": {
          "id": 1,
          "name": "John Doe",
          "email": "john.doe@school.com",
          "phoneNumber": "091234567",
          "address": "New York",
          "role": "LECTURER",
          "status": "ACTIVE"
        },
        "status": "ACTIVE"
      }
    ],
    "status": "ACTIVE",
    "createdDate": "2026-02-07T11:00:00",
    "createdBy": "admin01"
  }
]
```

### 7. Get Courses by Teacher

**Request:**
```http
GET http://localhost:8080/courses/teacher/1
Authorization: Bearer <your-jwt-token>
```

**Response:**
```json
[
  {
    "id": 1,
    "courseName": "Spring Boot Advanced",
    "teacherId": 1,
    "teacher": {
      "id": 1,
      "name": "John Doe",
      "email": "john.doe@school.com",
      "role": "LECTURER"
    },
    "status": "ACTIVE",
    "createdDate": "2026-02-07T10:35:00",
    "createdBy": "admin01"
  }
]
```

### 8. Update Teacher (without changing role)

**Request:**
```http
PUT http://localhost:8080/teachers/1
Authorization: Bearer <your-jwt-token>
Content-Type: application/json

{
  "name": "John Doe Updated",
  "email": "john.updated@school.com",
  "phoneNumber": "091234999",
  "address": "California"
}
```

**Note:** The role field is optional. If not provided, the existing role will be preserved.

### 9. Update Student Courses

**Request:**
```http
PUT http://localhost:8080/students/1
Authorization: Bearer <your-jwt-token>
Content-Type: application/json

{
  "name": "Jane Smith",
  "email": "jane.smith@student.com",
  "phoneNumber": "091234568",
  "address": "Los Angeles",
  "courseIdList": [1, 2, 3]
}
```

## 📁 Project Structure

```
springboot2026/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/spcourse/springboot2026/
│   │   │       ├── config/
│   │   │       │   ├── JwtAuthenticationFilter.java
│   │   │       │   ├── SecurityConfig.java
│   │   │       │   └── SecurityUtil.java
│   │   │       ├── controller/
│   │   │       │   ├── AuthController.java
│   │   │       │   ├── CourseController.java
│   │   │       │   ├── StudentController.java
│   │   │       │   └── TeacherController.java
│   │   │       ├── dto/
│   │   │       │   ├── BaseAuditableDTO.java
│   │   │       │   ├── CourseDTO.java
│   │   │       │   ├── LoginRequest.java
│   │   │       │   ├── StudentDTO.java
│   │   │       │   └── TeacherDTO.java
│   │   │       ├── entity/
│   │   │       │   ├── BaseAuditable.java
│   │   │       │   ├── Course.java
│   │   │       │   ├── Role.java
│   │   │       │   ├── SchoolUser.java
│   │   │       │   ├── Status.java
│   │   │       │   ├── Student.java
│   │   │       │   └── Teacher.java
│   │   │       ├── repository/
│   │   │       │   ├── CourseRepository.java
│   │   │       │   ├── StudentRepository.java
│   │   │       │   ├── TeacherRepository.java
│   │   │       │   └── UserRepository.java
│   │   │       ├── service/
│   │   │       │   ├── CourseService.java
│   │   │       │   ├── StudentService.java
│   │   │       │   ├── TeacherService.java
│   │   │       │   └── impl/
│   │   │       │       ├── CourseServiceImpl.java
│   │   │       │       ├── JwtService.java
│   │   │       │       ├── StudentServiceImpl.java
│   │   │       │       └── TeacherServiceImpl.java
│   │   │       └── Springboot2026Application.java
│   │   └── resources/
│   │       └── application.properties
│   └── test/
└── pom.xml
```

## 🔧 Key Components

### Security Configuration

- **JwtAuthenticationFilter**: Intercepts requests to validate JWT tokens
- **SecurityConfig**: Configures Spring Security with role-based access control
- **SecurityUtil**: Utility class to get current user information

### Entities

- **BaseAuditable**: Abstract class with audit fields (createdBy, createdDate, updatedBy, updatedDate)
- **SchoolUser**: User entity for authentication
- **Teacher**: Teacher entity with role
- **Course**: Course entity with teacher assignment
- **Student**: Student entity with course enrollments

### DTOs

Data Transfer Objects to decouple API layer from entity layer and provide nested relationship data.

## 🐛 Troubleshooting

### Common Issues

**1. 403 Forbidden Error**
- Make sure you include the JWT token in the Authorization header
- Verify that your user role has permission for the endpoint
- Check if your token hasn't expired

**2. Database Connection Error**
- Verify MySQL is running
- Check database credentials in `application.properties`
- Ensure the database `studentdb` exists

**3. JWT Key Error (WeakKeyException)**
- The JWT secret key must be at least 256 bits
- Use the provided secret key in `application.properties`

**4. Role Update Issue**
- When updating Teacher without providing role field, existing role is preserved
- To change role, explicitly include it in the request

## 👨‍💻 Author

Nang Mo Kham
This project is developed for educational purposes.

## 🙏 Acknowledgments

- Spring Framework Team
- Spring Boot Documentation
- JWT.io for token reference


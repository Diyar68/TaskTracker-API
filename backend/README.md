# TaskTracker-API

A simple REST API for managing tasks built with **Spring Boot**.
This project was created to learn the fundamentals of backend development with Java, Spring Boot, REST APIs, validation, testing, and database integration.

---

## Features

* Create new tasks
* View all tasks
* Update existing tasks
* Delete tasks
* Mark tasks as **IN_PROGRESS**
* Mark tasks as **DONE**
* Request validation
* Global exception handling
* Unit tests with JUnit & Mockito
* Controller tests with MockMvc
* PostgreSQL database
* H2 database for development/testing
* Global CORS configuration

---

## Tech Stack

* Java 21
* Spring Boot
* Spring Web
* Spring Data JPA
* PostgreSQL
* H2 Database
* Maven
* JUnit 5
* Mockito
* MockMvc

---

## Project Structure

```text
src
├── controller
├── dto
├── entity
├── exception
├── mapper
├── repository
├── service
└── config
```

---

## API Endpoints

| Method | Endpoint                  | Description              |
| ------ | ------------------------- | ------------------------ |
| GET    | `/tasks`                  | Get all tasks            |
| POST   | `/tasks`                  | Create a new task        |
| PUT    | `/tasks/{id}`             | Update a task            |
| DELETE | `/tasks/{id}`             | Delete a task            |
| PATCH  | `/tasks/{id}/done`        | Mark task as DONE        |
| PATCH  | `/tasks/{id}/in-progress` | Mark task as IN_PROGRESS |

---

## Example Request

### Create Task

**POST** `/tasks`

```json
{
  "description": "Learn Spring Boot"
}
```

### Example Response

```json
{
  "id": 1,
  "description": "Learn Spring Boot",
  "status": "TODO"
}
```

---

## Validation

Requests are validated before they reach the service layer.

Example:

```json
{
  "description": ""
}
```

returns **400 Bad Request**.

---

## Error Handling

The application uses a global exception handler.

Example:

```json
{
  "status": 404,
  "error": "Not Found",
  "message": "Task with id 5 does not exist",
  "path": "/tasks/5",
  "timestamp": "2026-07-07T12:30:15"
}
```

---

## Running the Project

### 1. Clone the repository

```bash
git clone https://github.com/Diyar68/TaskTracker.git
```

### 2. Start PostgreSQL

If you're using Homebrew:

```bash
brew services start postgresql@18
```

### 3. Configure the database

Update your `application.properties` (or `application.yml`) with your PostgreSQL credentials.

Example:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/tasktracker
spring.datasource.username=postgres
spring.datasource.password=your_password
```

### 4. Start the application

```bash
./mvnw spring-boot:run
```

or

```bash
mvn spring-boot:run
```

The API will be available at:

```text
http://localhost:8080
```

---

## Running Tests

Run all tests with:

```bash
mvn test
```

---

## Future Improvements

* React frontend
* Authentication with JWT
* User accounts
* Search and filtering
* Pagination
* Docker support
* Deployment
* CI/CD with GitHub Actions

---

## Learning Goals

This project was built to practice:

* REST API development
* Spring Boot architecture
* Layered architecture
* DTOs and mapping
* Validation
* Exception handling
* Testing with JUnit and Mockito
* Database integration with PostgreSQL
* Clean and maintainable backend development

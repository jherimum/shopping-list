# Shopping List App

A Spring Boot REST API application for managing shopping lists.

## Features

- Create, read, update, and delete shopping items
- H2 in-memory database
- JPA/Hibernate for data persistence

## Technologies

- Java 21
- Spring Boot 3.5.5
- Spring Data JPA
- H2 Database
- Lombok
- Maven

## Running the Application

### Prerequisites

- Java 21
- Maven 3.6+

### Local Development

1. Clone the repository
2. Run the application:
   ```bash
   mvn spring-boot:run
   ```
3. The application will start on http://localhost:8080

### API Documentation

Once the application is running, you can access the interactive API documentation:

- **Swagger UI**: http://localhost:8080/swagger-ui/index.html
- **OpenAPI JSON**: http://localhost:8080/v3/api-docs

The Swagger UI provides an interactive interface to test all the API endpoints directly from your browser.

### Using Docker

1. Build the Docker image:
   ```bash
   docker build -t shopping-list-app .
   ```

2. Run the container:
   ```bash
   docker run -p 8080:8080 shopping-list-app
   ```

3. Access the application at http://localhost:8080


## Testing

Run tests with:
```bash
mvn test
```
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
- Maven 3.6+ (or use the included Maven wrapper)

### Local Development

1. Clone the repository
2. Run the application:
   ```bash
   ./mvnw spring-boot:run
   ```
3. The application will start on http://localhost:8080

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

## API Endpoints

Base URL: `http://localhost:8080/shopping-items`

### Get All Shopping Items
- **GET** `/shopping-items`
- **Description**: Retrieve a paginated list of all shopping items
- **Parameters**: 
  - `page` (optional): Page number (default: 0)
  - `size` (optional): Page size (default: 20)
  - `sort` (optional): Sort criteria (e.g., `name,asc`)
- **Response**: Paginated list of shopping items

### Create Shopping Item
- **POST** `/shopping-items`
- **Description**: Create a new shopping item
- **Request Body**:
  ```json
  {
    "name": "string",
    "price": "decimal",
    "quantity": "integer",
    "category": "string"
  }
  ```
- **Response**: Created shopping item with generated ID

### Get Shopping Item by ID
- **GET** `/shopping-items/{id}`
- **Description**: Retrieve a specific shopping item by its ID
- **Parameters**: 
  - `id` (path): Shopping item ID
- **Response**: Shopping item details

### Update Shopping Item
- **PUT** `/shopping-items/{id}`
- **Description**: Update an existing shopping item
- **Parameters**: 
  - `id` (path): Shopping item ID
- **Request Body**:
  ```json
  {
    "name": "string",
    "price": "decimal",
    "quantity": "integer",
    "category": "string"
  }
  ```
- **Response**: Updated shopping item

### Delete Shopping Item
- **DELETE** `/shopping-items/{id}`
- **Description**: Delete a shopping item by its ID
- **Parameters**: 
  - `id` (path): Shopping item ID
- **Response**: Empty response with 200 status

## Testing

Run tests with:
```bash
./mvnw test
```
# User, Email, and Notification Management System

This project is a microservices architecture composed of the following services:
- **User**: Manages users, including login and permissions.
- **Email**: Manages emails associated with users.
- **Notification**: Sends notifications to administrators about email events.
- **Eureka Server**: Performs service discovery.
- **API Gateway**: Intermediates calls between microservices and controls access to endpoints based on user permissions.

## Technologies Used

- **Java 17**
- **Spring Boot**
- **Spring Data JPA**
- **Spring Cloud Eureka**
- **Spring Cloud Gateway**
- **Spring Security**
- **Spring Kafka**
- **Java Mail Sender**
- **MySQL**
- **Docker**
- **OpenFeign** 
- **Lombok**
- **JUnit5**
- **ModelMapper**
- **Flyway**
- **Jwt**

## Service Structure

1. **User Service**
    - Manages user data.
    - Example of a MySQL table:
    ```sql
    CREATE TABLE usuario (
        id INT PRIMARY KEY AUTO_INCREMENT,
        nome VARCHAR(200) NOT NULL,
        cpf VARCHAR(50),
        password VARCHAR(200),
        data_criacao DATE,
        data_atualizacao DATE,
        eh_admin BOOLEAN
    );
    ```

2. **Email Service**
    - Manages users' emails.
    - Example of a MySQL table:
    ```sql
    CREATE TABLE email (
        id INT PRIMARY KEY AUTO_INCREMENT,
        usuario_id INT NOT NULL,
        email VARCHAR(150) NOT NULL,
        data_criacao DATE NOT NULL,
        eh_admin BOOLEAN NOT NULL
    );
    ```

3. **Notification Service**
    - Sends asynchronous notifications to administrators about email-related events.
    
4. **Service Discovery (Eureka Server)**
    - All microservices are registered with Eureka.

5. **API Gateway**
    - Controls routing and request security.
  
6. **Kafka and Zookeeper**
    - Manages asynchronous connections between services.
    **Note: The Docker-compose file is located in the root of the project and for Kafka** 

## API Gateway Configuration

The **API Gateway** routes requests to microservices and enforces security based on the `eh_admin` field in the **User** table.

### Security Rules

- Only users with `eh_admin = true` can access admin endpoints.
- Security is configured in the Gateway to validate the JWT token and check permissions.

## Postman Collection Documentation
- [https://documenter.getpostman.com/view/25729709/2sAXqnej4C](https://documenter.getpostman.com/view/25729709/2sAXqnej4C)

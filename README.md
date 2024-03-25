# Java Spring Boot Project - Microservices Architecture

This repository contains a Java Spring Boot project implementing a microservices-based architecture. Below are descriptions of each service:

## Services

### API Gateway

The API Gateway service serves as the entry point for clients to access the microservices in the system. It provides routing, authentication, and other cross-cutting concerns.

### Config Server

The Config Server service is responsible for centralized configuration management. It allows microservices to retrieve their configuration from a central location, facilitating easier configuration management and versioning.

### Course Service

The Course Service manages information related to courses offered within the system. It handles operations such as creating, updating, and retrieving course data.

### Enroll Service

The Enroll Service handles enrollment-related operations, such as registering students for courses and managing enrollment status.

### Eureka Server

The Eureka Server is a service registry and discovery server. It allows microservices to register themselves and discover other services in the system dynamically.

### Instructor Service

The Instructor Service manages information about instructors or teachers within the system. It provides functionalities for adding, updating, and retrieving instructor data.

### Student Service

The Student Service manages information about students enrolled in courses. It handles operations such as student registration, profile management, and course enrollment.


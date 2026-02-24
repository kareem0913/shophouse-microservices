# ShopHouse Microservices

> A modern, scalable e-commerce backend built with Spring Boot microservices architecture, featuring service discovery, API gateway, circuit breakers.

![Java](https://img.shields.io/badge/Java-21-orange?style=flat-square)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.0+-green?style=flat-square)
![Docker](https://img.shields.io/badge/Docker-Compose-blue?style=flat-square)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-15-336791?style=flat-square)

---

## 🎯 Overview

**ShopHouse** is a distributed microservices architecture. It demonstrates enterprise-level patterns including service discovery, API gateway, resilience patterns, centralized configuration, and inter-service communication.

---

## 🏗️ Architecture

The system follows a **microservices-based architecture** with the following key components:

```
┌─────────────┐
│   Client    │
└──────┬──────┘
       │
       ▼
┌─────────────────────────────────────┐
│   API Gateway (Spring Cloud Gateway)│ (Port 8075)
│  • Routing & Load Balancing         │
│  • JWT Authentication               │
│  • Circuit Breakers                 │
└──────┬──────┬──────┬──────┬──────┬──┘
       │      │      │      │      │
   ┌───┴──┬───┴──┬───┴──┬───┴──┬───┴──┐
   │      │      │      │      │      │
   ▼      ▼      ▼      ▼      ▼      ▼
  Cart  Users Products Orders Files  (Microservices)
┌────────────────────────────────────────┐
│  Service Discovery (Eureka)  (Port 8074)│
│  • Service Registration                │
│  • Health Monitoring                   │
└────┬───────────────────────────────────┘
     │
     ▼
┌─────────────────────────────────────┐
│  Config Server (Spring Cloud Config)│ (Port 8072)
│  • Centralized Configuration        │
│  • Environment-Specific Props       │
└─────────────────────────────────────┘
     │
     ▼
┌─────────────────────────────────────┐
│  PostgreSQL Database                │ (Port 5433)
│  • cart, orders, products, users    │
└─────────────────────────────────────┘
```

---


## 📁 Project Structure

```
shophouse-microservices/
├── configserver/              # Spring Cloud Config Server
│   ├── src/main/java/
│   ├── src/main/resources/
│   │   └── config/           # Config files for all services
│   └── pom.xml
│
├── eurekaserver/              # Eureka Service Discovery
│   ├── src/main/java/
│   ├── src/main/resources/
│   └── pom.xml
│
├── gatewayserver/             # Spring Cloud API Gateway
│   ├── src/main/java/
│   │   ├── filter/           # Custom gateway filters
│   │   ├── security/         # JWT token provider
│   │   ├── config/           # Route configurations
│   │   └── util/
│   ├── src/main/resources/
│   └── pom.xml
│
├── cart/                       # Cart Microservice
│   ├── src/main/java/
│   │   ├── controller/
│   │   ├── service/
│   │   ├── entity/
│   │   └── dto/
│   ├── src/main/resources/
│   └── pom.xml
│
├── users/                      # Users & Auth Microservice
│   ├── src/main/java/
│   ├── src/main/resources/
│   └── pom.xml
│
├── products/                   # Products Microservice
│   ├── src/main/java/
│   ├── src/main/resources/
│   └── pom.xml
│
├── orders/                     # Orders Microservice
│   ├── src/main/java/
│   │   ├── service/
│   │   │   └── client/       # Feign clients for inter-service calls
│   │   └── ...
│   ├── src/main/resources/
│   └── pom.xml
│
├── files/                      # Files Management Service
│   ├── src/main/java/
│   ├── src/main/resources/
│   └── pom.xml
│
├── docker-compose.yml          # Docker Compose configuration
├── shophouse-microservice.postman_collection.json
└── README.md
```

---

## 🔧 Services

### 1. **Config Server** (Port 8072)
- Centralized configuration management using Spring Cloud Config
- Stores environment-specific properties for all services
- Enables dynamic property updates without service restarts
- **Startup Order:** Starts first

### 2. **Eureka Server** (Port 8074)
- Service discovery and registration using Netflix Eureka
- Monitors health of all microservices
- Enables dynamic service-to-service communication
- **Startup Order:** Starts after Config Server
- **UI:** `http://localhost:8074/` — View registered services

### 3. **API Gateway** (Port 8075)
- Central entry point for all client requests using Spring Cloud Gateway
- Dynamic routing to microservices based on service names
- **Key Features:**
  - JWT-based authentication for protected routes
  - Circuit breaker patterns with fallback mechanisms
  - Automatic retry logic for transient failures
  - Request/response filtering
- **Startup Order:** Starts last (depends on all services)

### 4. **Microservices** (Dynamic Ports)

#### **Cart Service** (Port 8080)
- Shopping cart management
- Add, update, remove items from cart
- Inter-service calls to Products via Feign Client
- Uses resilience4j circuit breaker for fault tolerance

#### **Users Service** (Port 8071)
- User authentication and authorization
- User profile management
- JWT token generation and validation
- Role-based access control (RBAC)

#### **Products Service** (Port 8070)
- Product catalog management
- Category organization
- Product search and filtering
- Image and media handling

#### **Orders Service** (Port 8081)
- Order creation and management
- Order history and tracking
- Integration with Cart, Users, and Products services
- Multiple circuit breaker fallbacks for dependent services

#### **Files Service** (Port 8073)
- File upload and management
- Image processing for products
- Persistent storage with Docker volumes

---

## ✨ Core Features

### 🔐 **Authentication & Security**
- **JWT (JSON Web Tokens)** for stateless authentication
- **Spring Security** integration with custom filters
- **Authorization**: Role-based access control (ADMIN, USER roles)
- Protected endpoints for cart and orders operations
- Token validation in API Gateway before routing

### 🔌 **Service-to-Service Communication**
- **OpenFeign**: Declarative HTTP client for inter-service calls
- **Service Discovery**: Automatic service location via Eureka
- Load balancing across service instances
- Support for multiple endpoints with fallback strategies

### 🛡️ **Resilience & Fault Tolerance**
- **Circuit Breaker** (Resilience4j)
  - Prevents cascading failures
  - Sliding window: 10 requests
  - Failure rate threshold: 50%
  - Wait duration: 10 seconds (open state)
  - Fallback mechanisms for graceful degradation
  
- **Retry Mechanism**
  - Automatic retry on transient failures
  - Exponential backoff strategy
  - Maximum 3 retries with progressive delays

- **Timeout Management**
  - Gateway HTTP client timeout: 2 seconds (connection)
  - Response timeout: 7 seconds
  - Prevents hanging requests

### 📊 **Configuration Management**
- **Spring Cloud Config Server** for centralized property management
- Environment-specific configurations:
  - `*-docker.properties` — Production/Docker environment
  - `*-local.properties` — Local development
- Dynamic refresh without downtime
- Secure property storage

### 💾 **Data Persistence**
- **PostgreSQL** database
- **Spring Data JPA** with Hibernate ORM
- **MapStruct** for DTO mapping
- Database auto-initialization on container startup
- Automatic creation of service-specific databases

### 📦 **File Management**
- Persistent volumes for uploaded files
- Category and product image storage
- Docker volume mapping for durability

---

## 🛠️ Technology Stack

| Component | Technology | Version |
|-----------|-----------|---------|
| **Language** | Java | 21 |
| **Framework** | Spring Boot | 4.0+ |
| **Config Management** | Spring Cloud Config | 2025.1.0 |
| **Service Discovery** | Netflix Eureka | Spring Cloud |
| **API Gateway** | Spring Cloud Gateway | Spring Cloud |
| **Inter-Service Communication** | OpenFeign | Spring Cloud |
| **Resilience** | Resilience4j | Spring Cloud |
| **Database** | PostgreSQL | 15 (Alpine) |
| **ORM** | Spring Data JPA / Hibernate | 6.4+ |
| **Validation** | Spring Validation | 6.0+ |
| **Mapping** | MapStruct | 1.6.3 |
| **Security** | Spring Security + JWT | Spring 6.0+ |
| **API Docs** | SpringDoc OpenAPI (Swagger 3.0) | 2.8.8 |
| **Containerization** | Docker & Docker Compose | Latest |
| **Build Tool** | Maven | 3.6+ |

---

## 🚀 Quick Start

### Prerequisites
- Docker & Docker Compose installed on your system
- No manual database setup required (auto-initialized)

### Installation & Startup

1. **Download Docker Compose file:**
   ```bash
   wget https://raw.githubusercontent.com/kareem0913/shophouse-microservices/main/docker-compose.yml
   ```

2. **Download Postman Collection:**
   ```bash
   wget https://raw.githubusercontent.com/kareem0913/shophouse-microservices/main/shophouse-microservice.postman_collection.json
   ```

3. **Start all services:**
   ```bash
   docker compose up
   ```

### Stopping Services
```bash
docker compose down
```

Remove volumes (reset database):
```bash
docker compose down -v
```

---

## 📚 API Documentation

### Using Postman Collection

1. Import the downloaded `shophouse-microservice.postman_collection.json` into Postman
2. All API endpoints are pre-configured with:
   - Correct request URLs (routed through API Gateway)
   - Environment variables for base URLs
   - Authentication headers

---

## 🔌 Circuit Breaker Actuator URLs

This project uses Resilience4j (via Spring Cloud Circuit Breaker) for fault tolerance. Each service that uses circuit breakers exposes actuator endpoints that let you inspect circuit breaker status and events. Below are the commonly available endpoints and the correct URLs for each service (replace host/port if you changed them in `docker-compose.yml`).

Service URLs (default ports used in this repository):

- API Gateway (GatewayServer) — port 8075
  - Circuit breakers list: http://localhost:8075/actuator/circuitbreakers
  - Circuit breaker events: http://localhost:8075/actuator/circuitbreakerevents

- Products Service — port 8070
  - Circuit breakers list: http://localhost:8070/actuator/circuitbreakers
  - Circuit breaker events: http://localhost:8070/actuator/circuitbreakerevents

- Cart Service — port 8080
  - Circuit breakers list: http://localhost:8080/actuator/circuitbreakers
  - Circuit breaker events: http://localhost:8080/actuator/circuitbreakerevents

- Orders Service — port 8081
  - Circuit breakers list: http://localhost:8081/actuator/circuitbreakers
  - Circuit breaker events: http://localhost:8081/actuator/circuitbreakerevents

---

## 📧 Contact

- **Email:** kareem.345@outlook.com

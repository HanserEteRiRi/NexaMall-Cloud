# NexaMall Cloud

## Project Overview

NexaMall Cloud is a cloud-native e-commerce platform built with Java, Spring Boot, and Spring Cloud. Its domain-oriented microservices cover authentication, users, sellers, products, carts, orders, promotions, settlement, risk control, object storage, search, scheduled jobs, API routing, and administration.

The platform uses Nacos for service discovery and configuration, Spring Cloud Gateway for API routing, OpenFeign for service communication, MySQL and MyBatis-Plus for persistence, Redis for caching, RabbitMQ for asynchronous messaging, Elasticsearch for search, and Seata for distributed transactions. The project is organized as a Maven multi-module application and includes Docker Compose infrastructure for local development.

## Getting Started

### Prerequisites

- JDK 21
- Docker Engine with Docker Compose v2
- At least 8 GB of available memory

The Maven Wrapper is included, so a separate Maven installation is not required.

### 1. Create the Local Environment File

Linux or macOS:

```bash
cp .env.example .env
```

PowerShell:

```powershell
Copy-Item .env.example .env
```

Review the values in `.env` before starting the services. The defaults are intended for local development only.

### 2. Start the Infrastructure

```bash
docker compose -f compose.legacy.yml up -d
docker compose -f compose.legacy.yml ps
```

This starts MySQL, Redis, RabbitMQ, Nacos, Seata, and Elasticsearch.

### 3. Configure Nacos

1. Open `http://localhost:8848/nacos`.
2. Create the `shop` group.
3. Create the required service Data IDs using the examples in `doc/properties`.
4. Update database, Redis, RabbitMQ, and service addresses for the local environment.

### 4. Build the Project

Linux or macOS:

```bash
./mvnw -B -ntp -f shop-dependencies/pom.xml install
./mvnw -B -ntp -DskipTests clean package
```

PowerShell:

```powershell
.\mvnw.cmd -B -ntp -f shop-dependencies\pom.xml install
.\mvnw.cmd -B -ntp -DskipTests clean package
```

### 5. Start the Services

Start services in the following order:

1. `shop-auth`
2. `shop-platform`, `shop-user`, and `shop-goods`
3. `shop-order`, `shop-cart`, and `shop-activity`
4. Other required business services
5. `shop-gateway`
6. `shop-manage`

Run a service with the Maven Wrapper. For example:

Linux or macOS:

```bash
./mvnw -pl shop-gateway -am spring-boot:run
```

PowerShell:

```powershell
.\mvnw.cmd -pl shop-gateway -am spring-boot:run
```

Confirm that each service is registered in Nacos before starting services that depend on it.

### 6. Stop the Infrastructure

```bash
docker compose -f compose.legacy.yml down
```

# NexaMall Cloud

## Project Overview

NexaMall Cloud is a distributed e-commerce system built with Spring Cloud. The name “Nexa” comes from “Nexus” and represents a connected commerce network composed of customer, product, cart, order, promotion, seller, settlement, platform, job, risk, object storage, and search domains.

The project uses a multi-module Maven structure and domain-oriented service boundaries. It covers common microservice concerns including API gateway routing, service discovery and configuration, inter-service communication, authentication and authorization, caching, messaging, distributed transactions, persistence, search, and administration. The project is currently being modernized: the verified transition baseline uses Java 17 and Spring Boot 2.6, while the target architecture will use Java 21, Spring Boot 3.5, and Spring Security 6.

## Technology Stack

### Current Verified Stack

| Category | Technology |
| --- | --- |
| Language | Java 17, built with JDK 21 |
| Core Framework | Spring Boot 2.6.13, Spring Framework 5 |
| Microservices | Spring Cloud 2021.0.5, Spring Cloud Alibaba 2021.0.6.0 |
| Discovery and Configuration | Nacos |
| API Gateway | Spring Cloud Gateway |
| Service Communication | OpenFeign, Spring Cloud LoadBalancer |
| Resilience and Governance | Resilience4j, Sentinel |
| Authentication and Authorization | Spring Security, Spring Security OAuth2 (to be replaced) |
| Data Access | MyBatis-Plus 3.3.0, Druid, MySQL 8 |
| Caching | Redis, Redisson |
| Messaging | RabbitMQ, Spring Cloud Stream |
| Distributed Transactions | Seata, ShardingSphere |
| Search | Elasticsearch, Spring Data Elasticsearch |
| Object Storage | Qiniu Cloud, Alibaba Cloud OSS |
| Testing | JUnit 5 |
| Build and Engineering | Maven Wrapper 3.9.11, Lombok, Docker Compose |

### Modernization Target Stack

| Category | Target Technology |
| --- | --- |
| Java | Java 21 LTS |
| Core Framework | Spring Boot 3.5, Spring Framework 6, Jakarta EE |
| Microservices | Spring Cloud 2025.0, Spring Cloud Alibaba 2025.0 |
| Security | Spring Security 6, Spring Authorization Server, OAuth 2.1, OIDC, JWT |
| Data | MyBatis-Plus Boot 3 Starter, Flyway, MySQL 8.4 LTS |
| Caching and Messaging | Redis 7, Redisson, RabbitMQ, functional Spring Cloud Stream model |
| Search | Elasticsearch 8, Spring Data Elasticsearch |
| Observability | Spring Boot Actuator, Micrometer, OpenTelemetry, Prometheus, Grafana |
| Testing | JUnit 5, Testcontainers, Awaitility |
| Deployment | OCI images, Docker Compose, Kubernetes, Helm |

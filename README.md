# NexaMall Cloud（云枢商城）

## 项目介绍

NexaMall Cloud 是一个基于 Spring Cloud 构建的分布式电商系统。“Nexa”源自 Nexus，寓意将用户、商品、购物车、订单、营销、商家、结算、平台、任务、风控、对象存储与搜索等业务域连接成完整的电商服务网络。

项目采用 Maven 多模块与领域化服务拆分，覆盖 API 网关、服务注册与配置、服务间调用、认证授权、缓存、消息、分布式事务、数据持久化、搜索和后台管理等典型微服务场景。目前项目处于现代化重构阶段：已完成 Java 17 与 Spring Boot 2.6 过渡基线，后续将迁移到 Java 21、Spring Boot 3.5 和 Spring Security 6 技术体系。

## 技术栈

### 当前已验证技术栈

| 分类 | 技术 |
| --- | --- |
| 开发语言 | Java 17，使用 JDK 21 构建 |
| 核心框架 | Spring Boot 2.6.13、Spring Framework 5 |
| 微服务 | Spring Cloud 2021.0.5、Spring Cloud Alibaba 2021.0.6.0 |
| 注册与配置 | Nacos |
| API 网关 | Spring Cloud Gateway |
| 服务调用 | OpenFeign、Spring Cloud LoadBalancer |
| 服务治理 | Resilience4j、Sentinel |
| 认证授权 | Spring Security、Spring Security OAuth2（待替换） |
| 数据访问 | MyBatis-Plus 3.3.0、Druid、MySQL 8 |
| 缓存 | Redis、Redisson |
| 消息队列 | RabbitMQ、Spring Cloud Stream |
| 分布式事务 | Seata、ShardingSphere |
| 搜索 | Elasticsearch、Spring Data Elasticsearch |
| 对象存储 | 七牛云、阿里云 OSS |
| 测试 | JUnit 5 |
| 构建与工程化 | Maven Wrapper 3.9.11、Lombok、Docker Compose |

### 现代化目标技术栈

| 分类 | 目标技术 |
| --- | --- |
| Java | Java 21 LTS |
| 核心框架 | Spring Boot 3.5、Spring Framework 6、Jakarta EE |
| 微服务 | Spring Cloud 2025.0、Spring Cloud Alibaba 2025.0 |
| 安全 | Spring Security 6、Spring Authorization Server、OAuth 2.1、OIDC、JWT |
| 数据 | MyBatis-Plus Boot 3 Starter、Flyway、MySQL 8.4 LTS |
| 缓存与消息 | Redis 7、Redisson、RabbitMQ、Spring Cloud Stream 函数式模型 |
| 搜索 | Elasticsearch 8、Spring Data Elasticsearch |
| 可观测性 | Spring Boot Actuator、Micrometer、OpenTelemetry、Prometheus、Grafana |
| 测试 | JUnit 5、Testcontainers、Awaitility |
| 部署 | OCI 镜像、Docker Compose、Kubernetes、Helm |

# 技术栈与版本状态

## 当前可构建技术栈

当前代码停留在用于跨越旧框架的稳定桥接点。

| 层次 | 当前版本/实现 | 说明 |
| --- | --- | --- |
| Java | Java 17 字节码，JDK 21 构建 | 已通过全量干净构建 |
| Spring Boot | 2.6.13 | 过渡版本，不是最终目标 |
| Spring Cloud | 2021.0.5 | 使用统一 release-train BOM |
| Spring Cloud Alibaba | 2021.0.6.0 | 与 Boot 2.6.13、Cloud 2021.0.5 匹配 |
| 服务发现/配置 | Nacos | 当前配置仍采用 `bootstrap*.yml` |
| 网关 | Spring Cloud Gateway | 已使用 Spring Cloud LoadBalancer |
| HTTP 客户端 | OpenFeign | 已迁移新版 `FallbackFactory` 包名 |
| 熔断 | Resilience4j | 已移除 Ribbon 和 Hystrix 依赖 |
| 安全 | 旧 Spring Security OAuth | 待整体替换，不应新增基于旧 API 的代码 |
| 数据访问 | MyBatis-Plus 3.3、Druid | 待升级 Boot 3 兼容版本 |
| 搜索 | Spring Data Elasticsearch 4、Elasticsearch 6 基础设施 | 仅作为旧环境复现，版本仍需统一升级 |
| 缓存 | Redis 7.2 容器 | 应用客户端仍属于 Boot 2 时代 |
| 消息 | RabbitMQ 3.13、Spring Cloud Stream 旧注解模型 | 待迁移函数式绑定模型 |
| 分布式事务 | Seata 1.8 容器 | 待升级并重新验证事务链路 |
| 测试 | JUnit 5 编译通过 | 现有测试很少，集成测试尚未形成基线 |
| 构建 | Maven Wrapper 3.9.11 | 54 模块 `clean package` 已通过 |

## 已确定的目标技术栈

目标栈按 2026 年仍在维护且与 Spring Cloud Alibaba 官方矩阵兼容的组合制定。版本只有在代码迁移和验证完成后才会转入“当前栈”。

| 层次 | 目标 | 迁移原则 |
| --- | --- | --- |
| Java | Java 21 LTS | 编译、运行、容器统一 JDK 21 |
| Spring Boot | 3.5.16 | 选择仍维护的 Boot 3.5 补丁版本 |
| Spring Cloud | 2025.0.3 | 采用 Northfields 维护版本 |
| Spring Cloud Alibaba | 2025.0.0.0 | 官方适配 Boot 3.5.x / Cloud 2025.0.x |
| Web API | Spring MVC 6、Jakarta EE | 全面迁移 `javax.servlet/validation/annotation` 到 `jakarta.*` |
| 认证服务器 | Spring Authorization Server | 使用标准授权码 + PKCE、客户端凭证和刷新令牌 |
| 资源服务器 | Spring Security OAuth2 Resource Server | JWT 验签，不再共享 Redis TokenStore |
| 服务调用 | OpenFeign + Spring Cloud LoadBalancer | Token 转发使用标准 OAuth2 Client |
| 韧性 | Resilience4j | 超时、重试、隔离、熔断按调用链配置 |
| 数据访问 | MyBatis-Plus 3.5.x Boot 3 Starter | 移除旧版本混用和隐式依赖 |
| 数据库迁移 | Flyway | 每个持久化服务独立维护版本脚本 |
| 数据库 | MySQL 8.4 LTS | 保留现有业务模型，先验证兼容再优化表结构 |
| 缓存 | Redis 7.x、Redisson Boot 3 Starter | 缓存键、TTL、锁语义可测试 |
| 消息 | RabbitMQ + Spring Cloud Stream 函数式模型 | 为关键事件增加 Outbox、幂等和重试策略 |
| 搜索 | Elasticsearch 8.x | 使用与 Spring Data Elasticsearch 版本匹配的 Java Client |
| 可观测性 | Actuator、Micrometer、OpenTelemetry | 指标、日志和 Trace 使用统一关联 ID |
| 测试 | JUnit 5、Testcontainers、Awaitility | 单测、组件测试和关键链路集成测试分层 |
| API 文档 | springdoc-openapi | 由代码生成并在 CI 验证 |
| 部署 | OCI 镜像、Compose 开发环境、Kubernetes/Helm 生产模板 | 非 root、只读文件系统、健康检查、资源限制 |

官方版本依据：

- [Spring Cloud Alibaba 2025.x 版本说明](https://sca.aliyun.com/en/docs/2025.x/overview/version-explain/)
- [Spring Boot 3.5 文档](https://docs.spring.io/spring-boot/3.5/)
- [Spring Cloud 2025.0 文档](https://docs.spring.io/spring-cloud-release/reference/2025.0/)

## 明确不再采用

- Netflix Ribbon、Hystrix。
- Spring Cloud Sleuth。
- `spring-security-oauth` 旧授权服务器。
- Redis `TokenStore` 作为所有服务共享认证状态。
- 将 Nacos 数据库导出、真实密码或云密钥提交到仓库。
- 依赖其他模块偶然传递进来的库。

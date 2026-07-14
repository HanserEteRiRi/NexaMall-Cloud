# 现代化重构路线

重构采用“每一阶段都可构建、可测试、可回滚”的方式推进。当前按用户要求停在 Phase 1，先完善文档；Phase 2 的 Boot 3 代码改动尚未开始。

## 总体原则

- 先建立验证能力，再改变运行时行为。
- 版本组合遵守 Spring Cloud Alibaba 官方兼容矩阵。
- 一个阶段只处理一类主要风险，并保留全 reactor 构建结果。
- 目标技术栈不能在未实现时写成当前能力。
- 不以“能够编译”代替启动、集成和业务链路验证。

## Phase 0：可复现基线——已完成

- 引入 Maven Wrapper 3.9.11。
- 安装并使用 JDK 21 构建工具链。
- 统一 UTF-8 编码和编译插件。
- 升级 Lombok 以支持 JDK 21 编译器。
- 清理 53 个子模块重复的编译插件配置。
- 添加本地 MySQL、Redis、RabbitMQ、Nacos、Seata、Elasticsearch Compose。
- 将 Nacos、Seata、Sentinel 和 OAuth 客户端参数改为环境变量。
- 删除包含环境地址和凭据的 Nacos 数据导出。

验收证据：54 模块在 JDK 21 下完成 `clean package -DskipTests`。

## Phase 1：Spring Boot 2.x 桥接——已完成

- Java 17 字节码。
- Spring Boot 2.6.13。
- Spring Cloud 2021.0.5。
- Spring Cloud Alibaba 2021.0.6.0。
- 使用统一 Spring Cloud release-train BOM。
- Ribbon 替换为 Spring Cloud LoadBalancer。
- Hystrix 替换为 Resilience4j。
- Feign `FallbackFactory` 迁移到当前 OpenFeign API。
- 移除 `hystrix.stream` 暴露端点。
- Spring Data Elasticsearch 查询迁移到 `ElasticsearchOperations`。
- 旧测试代码迁移到 JUnit 5 并完成测试编译。
- 显式声明 Jackson Java Time、Guava 等直接依赖。

验收证据：JDK 21 全量干净构建成功，所有模块以 `release 17` 编译。

## 当前暂停点

Phase 2 只完成了版本兼容调研，没有修改项目依赖或业务源码。已选定的目标组合为：

- Java 21。
- Spring Boot 3.5.16。
- Spring Cloud 2025.0.3。
- Spring Cloud Alibaba 2025.0.0.0。

继续重构时从 Phase 2 开始，不需要重复 Phase 0/1。

## Phase 2：Boot 3.5 与 Jakarta——待开始

主要工作：

1. 升级核心 BOM 和 Boot 3 兼容的 MyBatis-Plus、Druid、Redisson、数据库驱动。
2. 迁移 `javax.servlet`、`javax.validation`、`javax.annotation` 到 `jakarta.*`。
3. 将 Nacos 配置从 `bootstrap*.yml` 迁移到 `spring.config.import`。
4. 将 Spring Cloud Stream 的 `@EnableBinding`、`@StreamListener` 改为函数式 Consumer/Supplier。
5. 重新评估 ShardingSphere/Seata 集成，避免直接升级但事务语义未验证。
6. 完成所有 54 模块编译，再进行逐服务启动验证。

验收门槛：

- Java 21 `clean verify` 通过。
- 不存在业务侧 `javax.servlet/validation/annotation` 导入。
- 不存在 Ribbon、Hystrix、Sleuth、旧 Stream 注解。
- 核心服务能够连接 Nacos 并完成启动健康检查。

## Phase 3：认证授权——待开始

- `spring-security-oauth` 替换为 Spring Authorization Server。
- 使用 Spring Security 6 `SecurityFilterChain`。
- 浏览器/移动端采用 Authorization Code + PKCE。
- 服务间采用 Client Credentials。
- 资源服务本地验证 JWT 的 issuer、audience、签名和有效期。
- 设计旧 Redis Token 与新 JWT 的灰度切换和强制失效方案。
- 自定义短信/手机号登录通过标准扩展授权或认证转换器实现，不保留 password grant。

验收门槛：登录、刷新、登出、权限拒绝、服务间调用和密钥轮换测试全部通过。

## Phase 4：数据与测试基线——待开始

- 每个数据库服务建立 Flyway baseline。
- 将 `doc/base` 和 `doc/upgrade-*` 转换为可重复迁移脚本。
- 使用 Testcontainers 启动 MySQL、Redis、RabbitMQ 等真实依赖。
- 为用户、商品、购物车、下单、优惠和结算建立关键链路测试。
- 清理 Fastjson 1、过时 Guava/Hutool 等依赖，优先使用 Jackson 和 JDK API。

验收门槛：空数据库可自动迁移，升级数据库可重复验证，CI 执行核心组件测试。

## Phase 5：事件与可观测性——待开始

- Sleuth 替换为 Micrometer Tracing + OpenTelemetry。
- 指标通过 Actuator/Micrometer 暴露，日志包含 trace/span ID。
- 引入 OpenTelemetry Collector、Prometheus、Grafana 和日志后端开发配置。
- 关键领域事件采用 Outbox、幂等消费、死信和可观测重试。

验收门槛：一次下单链路可以从网关追踪到数据库和消息消费，并能定位失败事件。

## Phase 6：部署与安全——待开始

- 为可启动服务生成非 root OCI 镜像。
- 增加 readiness/liveness、资源限制、优雅停机和只读文件系统。
- 开发环境继续使用 Compose，生产提供 Kubernetes/Helm 模板。
- 在 CI 增加依赖漏洞、镜像、密钥和许可证扫描。
- 生产密钥由 Secret Manager/Kubernetes Secret 注入。

验收门槛：全新环境能够按文档部署，滚动升级不中断核心 API，安全扫描无未处置的高危问题。

## 安全遗留事项

原 Git 历史包含过环境凭据。删除当前文件不能清除历史对象，也不能撤销密钥。数据库、Redis、七牛云、阿里云和其他相关密钥必须在提供商侧轮换后，环境才能被视为安全。

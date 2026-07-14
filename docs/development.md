# 本地开发与构建

## 前置条件

- Eclipse Temurin JDK 21。
- Git。
- Docker Engine 和 Docker Compose v2。
- 至少 8 GB 可用内存；同时启动 Elasticsearch 和全部服务时建议更多。

项目自带 Maven Wrapper，不要求系统预装 Maven。

## 初始化环境变量

Linux/macOS：

```bash
cp .env.example .env
```

PowerShell：

```powershell
Copy-Item .env.example .env
```

`.env` 已被 Git 忽略。示例密码只用于本机开发，不能复用于共享环境或生产环境。

## 启动当前基础设施

```bash
docker compose -f compose.legacy.yml up -d
docker compose -f compose.legacy.yml ps
```

当前 Compose 包含 MySQL、Redis、RabbitMQ、Nacos、Seata 和 Elasticsearch。它用于复现过渡栈，组件版本会在 Boot 3 重构阶段更新。

停止环境：

```bash
docker compose -f compose.legacy.yml down
```

只有明确需要删除本地数据时才使用 `down -v`。

## Nacos 配置

1. 打开 `http://localhost:8848/nacos`。
2. 创建 `shop` 分组。
3. 参考 `doc/properties` 创建各服务需要的 Data ID。
4. 将数据库、Redis、RabbitMQ 地址替换为本机或环境变量对应值。
5. 不要把任何真实密钥写回仓库。

旧 Nacos 数据库导出已因包含环境凭据而删除，详见 `doc/base/NACOS_CONFIG_NOTICE.md`。

## 构建

全新 Maven 本地仓库必须先安装项目 BOM：

Linux/macOS：

```bash
./mvnw -B -ntp -f shop-dependencies/pom.xml install
./mvnw -B -ntp -DskipTests clean package
```

PowerShell：

```powershell
.\mvnw.cmd -B -ntp -f shop-dependencies\pom.xml install
.\mvnw.cmd -B -ntp -DskipTests clean package
```

`-DskipTests` 会编译测试但不执行测试。当前已验证全部 54 个模块可以在 JDK 21 下生成 Java 17 产物。

## 推荐启动顺序

1. MySQL、Redis、RabbitMQ、Nacos。
2. Seata 和 Elasticsearch（需要相关链路时）。
3. `shop-auth`。
4. `shop-platform`、`shop-user`、`shop-goods` 等基础业务服务。
5. `shop-order`、`shop-cart`、`shop-activity` 等组合业务服务。
6. `shop-gateway`。
7. `shop-manage` 和前端。

单模块启动示例：

```bash
./mvnw -pl shop-gateway -am spring-boot:run
```

由于配置来自 Nacos，服务启动成功不能只以进程存在判断；还应检查 Actuator 健康状态、Nacos 注册实例以及实际 API。

## 提交前检查

```bash
./mvnw -B -ntp -f shop-dependencies/pom.xml install
./mvnw -B -ntp -DskipTests clean package
git diff --check
```

后续测试基线建立后，提交门禁将改为执行单元测试、Testcontainers 组件测试和关键链路集成测试。

## 常见问题

### 根 POM 无法解析 `shop-dependencies`

先执行文档中的 BOM 安装命令。原因是根 POM 导入了仓库内部 BOM，而该 BOM 在 Maven 读取 reactor 前就必须可解析。

### Docker 命令存在但无法连接 daemon

确认 Docker Desktop 或系统 Docker Engine 已启动，并执行：

```bash
docker info
docker compose version
```

### 服务无法读取 Nacos 配置

检查 `NACOS_SERVER_ADDR`、`NACOS_NAMESPACE`、分组 `shop` 和 Data ID。当前代码仍使用 Boot 2 的 `bootstrap*.yml` 机制；Boot 3 阶段将迁移到 `spring.config.import=nacos:`。

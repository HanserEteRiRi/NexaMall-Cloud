# 架构与模块说明

## 总体结构

客户端通过 `shop-gateway` 访问业务服务。服务使用 Nacos 完成发现和配置，服务间通过 OpenFeign 调用。MySQL 按业务域拆库，Redis 提供缓存，RabbitMQ 承担异步消息，Elasticsearch 提供搜索，Seata 保留用于需要强一致性的跨库事务场景。

```text
Web / Admin / App
        |
  shop-gateway :10000
        |
        +-- shop-auth
        +-- shop-activity / shop-cart / shop-goods
        +-- shop-order / shop-settlement / shop-seller
        +-- shop-user / shop-platform / shop-risk
        +-- shop-job / shop-oss / shop-elasticsearch
        |
 Nacos / Redis / RabbitMQ / MySQL / Seata / Elasticsearch
```

## 服务目录

| 业务域 | 服务模块 | 端口 | 主要数据库 |
| --- | --- | ---: | --- |
| 网关 | `shop-gateway` | 10000 | 无 |
| 认证 | `shop-auth/shop-auth-server-api` | 8095 | `pager_auth` |
| 营销 | `shop-activity/shop-activity-api` | 11100 | `pager_activity` |
| 购物车 | `shop-cart/shop-cart-api` | 11150 | `pager_cart` |
| 搜索 | `shop-elasticsearch/shop-elasticsearch-api` | 11200 | Elasticsearch |
| 商品 | `shop-goods/shop-goods-api` | 11250 | `pager_goods` |
| 任务 | `shop-job/shop-job-api` | 11300 | `pager_job` |
| 管理后台 | `shop-manage` | 11350 | `pager_shop`、平台相关表 |
| 订单 | `shop-order/shop-order-api` | 11400 | `pager_order` |
| 对象存储 | `shop-oss` | 11450 | 云对象存储 |
| 平台 | `shop-platform/shop-platform-api` | 11500 | `pager_platform` |
| 风控 | `shop-risk/shop-risk-api` | 11550 | `pager_risk` |
| 商家 | `shop-seller/shop-seller-api` | 11600 | `pager_shop` |
| 结算 | `shop-settlement/shop-settlement-api` | 11650 | 待重构时确认归属 |
| 用户 | `shop-user/shop-user-api` | 11700 | `pager_shop` |

## Maven 模块约定

多数业务域由三类模块构成：

- `*-model`：领域模型、请求和响应对象。
- `*-client`：OpenFeign 客户端及降级实现。
- `*-api`：可启动服务、Controller、Service、Repository 和基础设施适配器。

公共模块：

- `shop-common`：通用返回结构、工具、序列化和共享配置。
- `shop-auth-resource`：当前共享的旧 OAuth2 资源服务器配置；目标是改造成标准 JWT 资源服务器 starter。
- `shop-dependencies`：全项目依赖版本 BOM。
- `sharding-transaction-base-seata-at`：旧 ShardingSphere/Seata 集成模块，后续需重新评估是否保留。

## 数据边界

业务服务应只直接访问自己拥有的数据库。跨域读取优先通过 API 或领域事件完成，不应直接连接其他服务的表。现有 `pager_shop` 被多个服务共享，是重构期间需要逐步拆分的遗留边界。

目标数据规则：

- 每个持久化服务拥有独立 Flyway 历史表和迁移目录。
- 关键写操作提供幂等键。
- 跨服务事件使用 Outbox 或等价的可靠发布机制。
- Seata 仅用于确实需要同步强一致性的少量链路，不作为默认事务方案。

## 安全边界

当前认证实现使用已停止维护的 Spring Security OAuth 和 Redis TokenStore，仅用于过渡构建。目标架构为：

```text
Client -- Authorization Code + PKCE --> Authorization Server
Client -- Bearer JWT --> Gateway / Resource Server
Service -- Client Credentials JWT --> Downstream Service
```

每个资源服务本地验证 JWT 的签名、签发者、受众和有效期；权限从标准 claim 映射为 Spring Security Authority。

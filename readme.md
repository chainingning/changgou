## 第1天

- 能够说出电商的商业行业特点
- 能够说出电商行业的技术特点
- 理解畅购技术架构
- ==掌握畅购的工程结构==
- ==能够完成畅购的工程搭建==
- ==能实现商品微服务搭建==
- 能实现品牌增删改查[通用 Mapper + PageHelper]



## 第2天

- 掌握 FastDFS 的工作原理以及 FastDFS的作用
- 能够实现 Docker 容器安装 FasrDFS
- 能够搭建文件上传微服务
- 实现相册管理(实战)(相册的增加、删除、修改、查询)
- 实现规格参数管理(实战)(规格的增加、删除、修改、查询)
- 实现商品分类管理(实战)(分类的增加、删除、修改、查询)



## 第3天

- 能够说出 SPU 与 SKU 的概念
- 能够实现新增商品、修改商品
- 能够实现商品审核、商家下架
- 能够实现品牌与分类关联管理
- 能够实现==删除==商品功能代码的编写
- 能够实现找回商品功能代码的编写
- 代码生成器[Controller，Service，ServiceImpl，Dao，Pojo，Swagger]



## 第4天

- 理解 Lua 的作用并且能编写最基本的 Lua 脚本
- 理解 OpenResty 的作用
- 能实现广告缓存载入与读取
- 掌握 Nginx 的基本配置，能使用 Nginx 发布网站首页
- 能理解 Canal 的工作流程
- 能基于 Canal 实现首页缓存同步



## 第5天

- 掌握在 Docker 环境下安装 ElasticSearch
- 掌握在 Docker 环境下给 ElasticSearch 配置 IK 分词器
- 掌握 Docker 环境下安装 Kibana
- 熟练使用 DSL 语句操作 ElasticSearch
- 实现 ES 导入商品搜索数据
- 实现商品关键字搜索
- 实现商品分类统计搜索
- 实现多条件搜索[品牌、规格条件搜索]



## 第6天

- 实现商品搜索条件筛选
- 实现品牌搜索规格过滤
- 实现商品搜索价格区间搜索
- 理解 ElasticSearch 权重讲解
- 实现商品搜索分页
- 实现商品搜索排序
- 实现商品搜索高亮



## 第7天

- 理解 Thymeleaf 模板引擎应用场景
- 掌握 Thymeleaf 常用标签
- 基于 Thymeleaf 实现商品搜索渲染
- 实现商品搜索 Thymeleaf 条件切换
- 实现商品详情页静态化工程搭建
- 商品详情页静态化功能实现



## 第8天

- 掌握微服务网关的系统搭建
- 理解什么是微服务网关以及它的作用
- 掌握系统中心微服务的搭建
- 掌握用户名密码加密存储 bcrypt
- 能够以说出 JWT 鉴权的组成
- 掌握 JWT 鉴权的使用
- 掌握网关使用 JWT 进行校验
- 掌握网关限流的实现



## 第9天

- 能够说出用户认证的流程
- 理解认证技术实现方案
- 掌握 SpringSecurity OAuth2.0 入门
- 理解 OAuth2.0 授权模式 - 重点理解授权码模式和密码授权模式
- 理解公钥私钥的校验流程
- 能实现基于 R5A 算法生成令牌
- 能实现用户授权认证开发



## 第10天

- 实现基于 OAuth + SpringSecurity 权限控制
- 实现 OAuth 认证微服务动态加载数据
- 理解购物车实现流程
- 实现购物车页面渲染
- 实现微服务 OAuth2.0 认证并获取用户令牌数据
- 实现微服务与微服务之间的认证



## 第11天

- 实现 OAuth 登录页的配置
- 实现 OAuth 登录成功跳转实现
- 实现结算页查询渲染
- 实现下单操作
- 实现下单修改库存
- 实现下单增加用户积分



## 第12天

- 掌握支付实现流程
- 能够说出微信支付开发的整体思路
- 实现生成支付二维码
- 能够编写查询支付状态
- 实现支付日志的生成与订单状态的改变、删除订单
- 实现支付状态回查
- 实现 MQ 处理支付回调状态
- 基于定时器实现定时处理订单状态



## 第13天

- 立即本地事务分布式事务
- 掌握 CAP 定理，并能够说出 CAP 定理中的组合流程
- 理解分布式事务实现方案
- 理解常见事务模型
- 掌握 RocketMQ 事务消息(介绍-数据最终一致)
- 理解 Fescar(seata) 事务模型并且能说出不同事务模型的优劣
- 基于 Fescar 分布式事务实现下单事务操作



## 第14天

- 掌握秒杀业务实现流程
- 能够实现秒杀商品压入 Redis 缓存
- 掌握 Spring 定时任务的使用
- 能够实现秒杀商品频道页展示
- 能够实现秒杀商品详情页展示
- 能够实现秒杀商品详情页倒计时
- 能够实现登录通用跳转控制
- 实现秒杀下单操作



## 第15天

- 能够说出削峰技术解决方案
- 基于 SpringBoot 的异步操作实现多线程下单
- 基于 Redis 队列实现防止秒杀重复排队
- 基于 Redis 解决并发超卖问题
- 能够说出支付流程
- 实现秒杀订单支付
- 能够实时超时支付订单库存回滚



## 第16天

- 理解集群和分布式的概念
- 可以熟练搭建 Eureka 集群
- 理解 Redis 集群的原理并能搭建 Redis 集群
- 理解 Redis 哨兵策略以及哨兵策略的作用
- 掌握解决 Redis 击穿问题的方案
- 能够说出 Redis 雪崩解决方案
- 能够搭建 RabbitMQ 集群
- 在项目中可以操作 RabbitMQ 集群


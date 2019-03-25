# CommunityGroup
## 基于spring全家桶的微服务项目
### 实现交友，吐槽，求职，提问，站内通信，关注等功能
### 该项目基于Spring Boot+ Spring Cloud框架，使用Spring Security进行权限管理，使用MySQL和MongoDB存储数据，使用Redis进行缓存，使用RabbitMq实现短信，站内信等异步发送，使用Elasticsearch实现了站内的全文搜索。
### 开发环境
- JDK1.8
- 数据库mysql 5.7
- MongoDB最新
- 开发工具 idea 2017.1.2
- maven版本3.3.9
- docker 最新版本
- centos7
本项目的所有开环境都是基于docker的
### 模块分类
|           模块           |      功能      |
| :----------------------: | :------------: |
|  communitygroup_common   |    公共模块    |
|  communitygroup_article  |   文章微服务   |
|   communitygroup_base    |   基础微服务   |
|  communitygroup_friend   |   交友微服务   |
| communitygroup_gathering |   活动微服务   |
|    communitygroup_qa     |   问答微服务   |
|  communitygroup_recruit  |   招聘微服务   |
|   communitygroup_user    |   用户微服务   |
|   communitygroup_spit    |   吐槽微服务   |
|  communitygroup_search   |   搜索微服务   |
|    communitygroup_web    | 前台微服务网关 |
|  communitygroup_manager  | 后台微服务网关 |
|  communitygroup_eureka   |    注册中心    |
|  communitygroup_config   |    配置中心    |
|    communitygroup_sms    |   短信微服务   |
### SQL文件
MySQLSource目录下是本项目的SQL建表文件
### 详细待更


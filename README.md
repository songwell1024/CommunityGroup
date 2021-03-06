# CommunityGroup
## 基于spring全家桶的微服务项目
### 实现参考文章、提问，吐槽，站内信，赞踩，关注，作业，搜索，安全测试工具等功能
### 该项目基于Spring Boot+ Spring Cloud框架，使用Spring Security进行权限管理，使用MySQL和MongoDB存储数据，使用Redis进行缓存，使用RabbitMq实现测试任务、短信，站内信等异步发送，使用Elasticsearch实现了站内的全文搜索。
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
|   communitygroup_base    |   基础工具微服务   |
|  communitygroup_friend   |   交友微服务   |
| communitygroup_gathering |   活动微服务   |
|    communitygroup_qa     |   问答微服务   |
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
### ElasticSearch

- 搜索引擎使用的ElasticSearch

  ElasticSearch默认不支持跨域访问，需要修改elasticsearch的配置，让其允许跨域访问。
  修改elasticsearch配置文件：elasticsearch.yml，增加以下两句命令：

  ```
  http.cors.enabled: true
  http.cors.allow‐origin: "*"
  ```

- 分词器使用的IK Analyzer

- MySQL数据导入到ElasticSearch使用了logstash-5.6.8

  关于logstash的配置:

  （1）在logstash-5.6.8安装目录下创建文件夹mysqletc （名称随意）
  （2）文件夹下创建mysql.conf （名称随意） ，内容如下：

  ```
  input {
    jdbc {
  	  # mysql jdbc connection string to our backup databse
  	  jdbc_connection_string => "jdbc:mysql://121.0.0.0:3306/communitygroup_article?useUnicode=true&characterEncoding=utf-8&allowMultiQueries=true&useSSL=false"
  	  # the user we wish to excute our statement as
  	  jdbc_user => "root"
  	  jdbc_password => "root"
  	  # the path to our downloaded jdbc driver  
  	  jdbc_driver_library => "D:\logstash-5.6.8\mysqletc\mysql-connector-java-5.1.46.jar"
  	  # the name of the driver class for mysql
  	  jdbc_driver_class => "com.mysql.jdbc.Driver"
  	  jdbc_paging_enabled => "true"
  	  jdbc_page_size => "20"
  	  #以下对应着要执行的sql的绝对路径。
  	  #statement_filepath => ""
  	  statement => "select id, title, content, state from tb_article"
  	  #定时字段 各字段含义（由左至右）分、时、天、月、年，全部为*默认含义为每分钟都更新（测试结果，不同的话请留言指出）
        schedule => "* * * * *"
    }
  }
  
  output {
    elasticsearch {
  	  #ESIP地址与端口
  	  hosts => "121.0.0.0:9200" 
  	  #ES索引名称（自己定义的）
  	  index => "communitygroup_article"
  	  #自增ID编号
  	  document_id => "%{id}"
  	  document_type => "article"
    }
    stdout {
        #以JSON格式输出
        codec => json_lines
    }
  }
  ```

  （3）将mysql驱动包mysql-connector-java-5.1.46.jar拷贝至D:/logstash-5.6.8/mysqletc/ 下 。D:/logstash-5.6.8是你的安装目录
  （4）命令行下执行

  ```
  logstash ‐f ../mysqletc/mysql.conf
  ```

  默认每间隔1分钟就执行一次sql查询。更新索引库

### RabbitMQ消息队列

直接使用的direct模式，然后调用阿里大于的接口来发送短信（Ps现在阿里大于不给个人用户开放了，我是很早前申请的，开发的时候还能用）
同时安全测试工具任务以RabbitMQ方式异步执行

### Spring Security密码加密

Spring Security提供了BCryptPasswordEncoder类,实现Spring的PasswordEncoder接口使用BCrypt强哈希方法来加密密码。

默认使用spring security后，所有的地址都被spring security所控制了，所有的地址都会被拦截，我们目前只是需要用到BCrypt密码加密的部分，所以我们要添加一个配置类，配置为所有地址都可以匿名访问。

这个去官网看看到底是怎么回事就可以。
### 基于JWT的Token验证

使用JWT做微服务鉴权，统一配置拦截器，在拦截器中统一对Token信息进行验证解析，解析出来的用户信息放入Request域中，也就是把用户信息放到上下文中，在具体的controller层获取使用。

### Spring Cloud

主要使用的组件和功能包括如下：

| 模块                            | 功能                                                         |
| ------------------------------- | ------------------------------------------------------------ |
| 服务发现——Netflix Eureka        | 微服务的注册与发现                                           |
| 服务调用——Netflix Feign         | 微服务模块间的调用，默认负载均衡                             |
| 熔断器——Netflix Hystrix         | 防止服务调用时发生雪崩效应                                   |
| 服务网关——Netflix Zuul          | 所有访问微服务的网关，在网关层对用户的Token进行了转发,对管理员身份做了验证 |
| 分布式配置——Spring Cloud Config | 所有微服务配置的管理中心。配置中心使用的github做仓储，在config这个微服务下有仓库地址 |
| 消息总线 —— Spring Cloud Bus    | 自动通知微服务的配置的改变                                   |


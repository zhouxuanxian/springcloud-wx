# GuanKe Cloud Framework
GuanKe Cloud Framework
2018-12-29

1. 参考Document资料进行开发

    ```
    建议本地开发环境，代码提交到gitlab的代码必须是测试完成的代码
    
    1. 本地开发环境准备
      强烈建议使用docker-compose运行基础服务(sa-core,vinda-gate)和mysql等应用环境,然后再运行业务功能模块,其他模块的服务器可以按需启动,
    
    2. 测试环境-用于功能演示
      开发测试完成之后，提交代码到gitlab即可。
    
    3. Docker部署环境变量
    
    environment:
    EUREKA_HOST=sa-core
    EUREKA_PORT=8761
    RABBIT_MQ_HOST=rabbitmq
    RABBIT_MQ_PORT=5762
    RABBIT_MQ_USERNAME=guest
    RABBIT_MQ_PASSWORD=guest
    MYSQL_HOST=mysql
    MYSQL_PORT=3306
    MYSQL_DB=sa_v1
    MYSQL_ROOT_USERNAME=root
    MYSQL_ROOT_PASSWORD=vinda@2019!SalesAux
    REDIS_HOST=redis
    REDIS_PORT=6379
    REDIS_DB=10
    SPRING_PROFILES_ACTIVE=dev
    
      
    ```
    
2. 模块说明    

```text

-springcloud-wx
 |
 |-- eureka-server Eureka服务注册中心
 |
 |-- springboot-gate 经销宝网关 
 |
 |-- wx-service 微信
```

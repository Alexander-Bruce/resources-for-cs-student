通过java构建运行

1. 打开application.properties确保下列设置生效

   `#普通环境下使用localhost`
   `spring.datasource.url=jdbc:mysql://localhost:3306/experiment`

2. 构建maven

​	`mvn clean install`

3. 进入target文件夹运行war包

​	`cd target && \`

​	`java -jar 5-MyBatis-221002511-0.0.1.war`

通过docker构建运行

1. 打开application.properties确保下列设置生效
   `#docker环境下使用host.docker.internal`
   `spring.datasource.url=jdbc:mysql://host.docker.internal:3306/experiment`

2. 构建maven

   `mvn clean install`

3. 通过docker-compose构建

​	`docker-compose up --build`
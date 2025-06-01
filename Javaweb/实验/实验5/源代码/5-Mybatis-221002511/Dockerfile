FROM docker.unsee.tech/openjdk:23-jdk-slim

WORKDIR /app

ARG JAR_FILE=target/*.war
COPY ${JAR_FILE} app.war

ENTRYPOINT ["java", "-jar", "/app/app.war"]
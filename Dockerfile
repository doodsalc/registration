FROM maven:3.9.3-eclipse-temurin-17 AS build
WORKDIR /app
COPY src /app/src
COPY pom.xml /app
RUN mvn clean package -DskipTests
FROM openjdk:21-jdk-slim
WORKDIR /app
COPY smtp/ /app/smtp/
COPY --from=build /app/target/registration-0.0.1.jar /app/registration-0.0.1.jar
EXPOSE 8080 8090
ENTRYPOINT ["sh", "-c", "java -jar -Dspring.config.location=./smtp/config/  /app/smtp/fake-smtp-server-2.4.0.jar & java -jar /app/registration-0.0.1.jar"]

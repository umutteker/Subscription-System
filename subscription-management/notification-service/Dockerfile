FROM openjdk:17-jdk-slim

WORKDIR /app

COPY notification-service/target/*.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]
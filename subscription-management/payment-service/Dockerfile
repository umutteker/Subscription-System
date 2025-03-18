FROM openjdk:17-jdk-slim

WORKDIR /app

COPY payment-service/target/*.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]
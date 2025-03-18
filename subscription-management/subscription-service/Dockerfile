FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /app

# Ana projeyi ve tüm alt servisleri kopyala
COPY ../../ .

# Maven ile servisin JAR dosyasını üret
RUN mvn -f subscription-service/pom.xml dependency:go-offline

# Testleri çalıştır (eğer bir test başarısız olursa, Docker build başarısız olur)
RUN mvn -f subscription-service/pom.xml clean test

# Maven ile servisin JAR dosyasını üret
RUN mvn -f subscription-service/pom.xml clean package -DskipTests

# Java için lightweight bir image kullan
FROM openjdk:17-jdk-slim
WORKDIR /app

# Önceki aşamadan derlenen JAR dosyasını al
COPY subscription-service/target/*.jar app.jar

# Servisi çalıştır
ENTRYPOINT ["java", "-jar", "app.jar"]
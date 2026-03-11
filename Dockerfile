# Base image
FROM eclipse-temurin:17.0.4.1_1-jre

# Workdir
WORKDIR /app

# Build jarni qo‘shish
COPY target/Auth-Service-0.0.1-SNAPSHOT.jar app.jar

# Port
EXPOSE 8080

# Run
ENTRYPOINT ["java", "-jar", "app.jar"]
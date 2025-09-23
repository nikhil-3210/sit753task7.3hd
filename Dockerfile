# Use OpenJDK base image
FROM openjdk:17-jdk-slim

# Set working directory
WORKDIR /app

# Copy built JAR from target/
COPY target/task-management-api-0.0.1.jar app.jar

# Expose port
EXPOSE 8080

# Run Spring Boot app
ENTRYPOINT ["java", "-jar", "app.jar"]
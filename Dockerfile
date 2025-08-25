# Use Java 17 (or 21 if you’re on Spring Boot 3.x)
FROM eclipse-temurin:17-jdk

# Set working directory
WORKDIR /app

# Copy jar file
COPY target/*.jar app.jar

# Expose the port Render will assign
EXPOSE 10000

# Run the jar
ENTRYPOINT ["java","-jar","app.jar"]

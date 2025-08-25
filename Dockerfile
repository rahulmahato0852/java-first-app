# ---------- Stage 1: Build ----------
FROM maven:3.9.6-eclipse-temurin-17 AS builder
WORKDIR /app

# Copy pom.xml and download dependencies first (cache optimization)
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy the source code
COPY src ./src

# Build the jar
RUN mvn clean package -DskipTests

# ---------- Stage 2: Run ----------
FROM eclipse-temurin:17-jdk
WORKDIR /app

# Copy only the built jar from the builder stage
COPY --from=builder /app/target/*.jar app.jar

EXPOSE 10000
ENTRYPOINT ["java","-jar","app.jar"]

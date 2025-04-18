# ---- Stage 1: Build the application ----
FROM maven:3.9.6-eclipse-temurin-17 AS build

# Set the working directory
WORKDIR /app

# Copy pom.xml and download dependencies (layer caching)
COPY pom.xml .
COPY src ./src

# Build the project (creates target/*.jar)
RUN mvn clean package -DskipTests

# ---- Stage 2: Create the runtime image ----
FROM openjdk:17-jdk-slim

# Set the working directory
WORKDIR /app

# Copy the jar from the build stage
COPY --from=build /app/target/*.jar app.jar

# Expose the application port
EXPOSE 8080

# Command to run the jar
ENTRYPOINT ["java", "-jar", "app.jar"]

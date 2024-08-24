#FROM openjdk:17

#WORKDIR /app

#COPY target/*.jar productcatalog.jar

#EXPOSE 8082

#ENTRYPOINT ["java", "-jar", "productcatalog.jar"]

# Use a base image with Maven and Java installed
FROM maven:3.8.8-eclipse-temurin-17 AS build

# Set the working directory
WORKDIR /app

# Copy the pom.xml and the entire source code to the container
COPY pom.xml /app/
COPY src /app/src/

# Build the application
RUN mvn clean package -DskipTests

# Use a smaller base image for running the app
FROM eclipse-temurin:17-jdk

WORKDIR /app

# Copy the jar file from the previous stage
COPY --from=build /app/target/*.jar productcatalog.jar

# Expose the port
EXPOSE 8082

# Run the jar file
ENTRYPOINT ["java", "-jar", "productcatalog.jar"]


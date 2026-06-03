# Use an official OpenJDK runtime as a parent image
FROM eclipse-temurin:21-jdk-alpine

# Set the working directory in the container
WORKDIR /app

# Copy the pom.xml and the project files
COPY . .

# Build the application
RUN ./mvnw clean package -DskipTests

# Copy the built jar to the app directory
RUN cp target/*.jar app.jar

# Expose the port your Spring Boot app runs on
EXPOSE 8080

# Run the jar file
ENTRYPOINT ["java","-jar","app.jar"]
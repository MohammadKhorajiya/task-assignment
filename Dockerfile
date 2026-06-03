# Use an official OpenJDK runtime
FROM eclipse-temurin:21-jdk-alpine

# Set the working directory
WORKDIR /app

# Copy the project files
COPY . .

# Grant execute permission to the Maven wrapper
RUN chmod +x ./mvnw

# Build the application
RUN ./mvnw clean package -DskipTests

# Copy the built jar to the app directory
RUN cp target/*.jar app.jar

# Expose the port
EXPOSE 8080

# Run the jar file
ENTRYPOINT ["java","-jar","app.jar"]
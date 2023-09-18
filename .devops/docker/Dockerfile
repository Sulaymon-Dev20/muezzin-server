# Use a base image with Java 17 installed
FROM eclipse-temurin:17
# Set the working directory in the container
WORKDIR /app
# Copy the JAR file into the container
COPY ../../target/muezzin-0.0.1-SNAPSHOT.jar app.jar
# Expose the port on which the application will run
EXPOSE 5677
# Set the command to run the application when the container starts
CMD ["java", "-jar", "app.jar"]

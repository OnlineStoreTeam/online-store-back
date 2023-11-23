# Use an official OpenJDK runtime as a parent image
FROM openjdk:17

# Set the working directory to /app
WORKDIR /app

# Copy the current directory contents into the container at /app
COPY target/online-store-back-0.0.1.jar /app/

# Make port 8080 available to the world outside this container
EXPOSE 8080

# Run java -jar your-application.jar when the container launches
ENTRYPOINT ["java", "-jar", "online-store-back-0.0.1.jar"]
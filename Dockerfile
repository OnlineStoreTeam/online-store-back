# First stage: build the application
FROM maven:3.8.3 AS build
COPY . /app
WORKDIR /app
RUN mvn package -DskipTests

# Second stage: create a slim image
FROM openjdk:17
COPY --from=build /app/target/online-store-back-0.0.1.jar /app.jar

EXPOSE 5000

ENTRYPOINT ["java", "-jar", "/app.jar"]
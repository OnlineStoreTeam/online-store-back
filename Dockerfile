FROM maven:3.8.3 AS build
COPY . /app
WORKDIR /app
RUN mvn package -DskipTests

FROM openjdk:17
COPY --from=build /app/target/online-store-back-0.0.1.jar /app.jar

EXPOSE 4999

ENTRYPOINT ["java", "-jar", "/app.jar"]

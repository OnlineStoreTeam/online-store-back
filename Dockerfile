FROM ubuntu:latest AS build

RUN apt-get update
RUN apt-get install openjdk-17-jdk -y
COPY . .

RUN ./gradlew bootJar --no-daemon

FROM openjdk:17-jdk

EXPOSE 8080

COPY --from=build /build/libs/online-store-back.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]
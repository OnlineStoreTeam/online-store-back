FROM ubuntu:latest AS build

RUN apt-get update
RUN apt-get install openjdk-17-jdk -y
COPY . .

RUN chmod +x ./gradlew
RUN ./gradlew bootJar --no-daemon

FROM openjdk:17-jdk

EXPOSE 8080

COPY --from=build /build/libs/online-store-back.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]

FROM postgres

ENV POSTGRES_DB st_19qm
ENV POSTGRES_USER st_19qm_user
ENV POSTGRES_PASSWORD edgE7VGlhZCk2knu9L1SQkPM3JTJtypQ
EXPOSE 5432:5432
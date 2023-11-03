FROM maven:3.8.4-openjdk-17-slim as build

WORKDIR /app

COPY ./zam-project/pom.xml ./zam-project/.mvn /app/
COPY ./zam-project/.mvn/wrapper /app/.mvn/wrapper

RUN mvn dependency:go-offline


COPY ./zam-project/src /app/src


RUN mvn package -DskipTests


FROM openjdk:17-slim


ARG JAR_FILE=/app/target/*.jar

COPY --from=build ${JAR_FILE} app.jar


ENTRYPOINT ["java","-jar","/app.jar"]
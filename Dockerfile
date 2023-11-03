FROM maven:3.8.4-openjdk-17-slim as build

WORKDIR /app

COPY pom.xml ./
COPY .mvn .mvn/

RUN mvn dependency:go-offline

COPY src ./src/

RUN mvn package -DskipTests

FROM openjdk:17-slim

WORKDIR /app

COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080

CMD ["java", "-jar", "app.jar"]
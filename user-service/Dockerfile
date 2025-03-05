FROM maven:3.8.1-openjdk-17-slim as build

WORKDIR /app

COPY pom.xml /app/

COPY user-service/pom.xml /app/user-service/

WORKDIR /app
RUN mvn install -N

WORKDIR /app/user-service

RUN mvn dependency:go-offline -B

COPY user-service /app/user-service

RUN mvn clean package -D skipTests

FROM openjdk:17-alpine3.14

WORKDIR /app

COPY --from=build /app/user-service/target/*.jar /app/user.jar

ENTRYPOINT ["java", "-jar", "user.jar"]
FROM maven:3.8.1-openjdk-17-slim as build

WORKDIR /app

COPY pom.xml /app/

COPY order-service/pom.xml /app/order-service/

WORKDIR /app
RUN mvn install -N

WORKDIR /app/order-service

RUN mvn dependency:go-offline -B

COPY order-service /app/order-service

RUN mvn clean package -D skipTests

FROM openjdk:17-alpine3.14

WORKDIR /app

COPY --from=build /app/order-service/target/*.jar /app/order.jar

ENTRYPOINT ["java", "-jar", "order.jar"]
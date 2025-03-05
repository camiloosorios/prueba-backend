FROM maven:3.8.1-openjdk-17-slim as build

WORKDIR /app

COPY pom.xml /app/

COPY product-service/pom.xml /app/product-service/

WORKDIR /app
RUN mvn install -N

WORKDIR /app/product-service

RUN mvn dependency:go-offline -B

COPY product-service /app/product-service

RUN mvn clean package -D skipTests

FROM openjdk:17-alpine3.14

WORKDIR /app

COPY --from=build /app/product-service/target/*.jar /app/product.jar

ENTRYPOINT ["java", "-jar", "product.jar"]
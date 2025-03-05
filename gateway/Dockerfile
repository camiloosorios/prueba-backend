FROM maven:3.8.1-openjdk-17-slim as build

WORKDIR /app

COPY pom.xml /app/

COPY gateway/pom.xml /app/gateway/

WORKDIR /app
RUN mvn install -N

WORKDIR /app/gateway

RUN mvn dependency:go-offline -B

COPY gateway /app/gateway

RUN mvn clean package -D skipTests

FROM openjdk:17-alpine3.14

WORKDIR /app

COPY --from=build /app/gateway/target/*.jar /app/gateway.jar

ENTRYPOINT ["java", "-jar", "gateway.jar"]
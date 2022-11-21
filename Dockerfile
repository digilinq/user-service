FROM maven:3.6.0-jdk-11 AS build
WORKDIR /build
COPY pom.xml pom.xml
COPY logger logger
COPY api api
COPY web web
COPY repository repository
COPY service service
RUN mvn clean package -DskipTests

FROM openjdk:11
WORKDIR /work
COPY --from=build /build/web/target/users-service.jar users-service.jar
ENTRYPOINT ["java","-jar","-Dspring.profiles.active=stage","users-service.jar"]
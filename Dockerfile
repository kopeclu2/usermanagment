FROM gradle:8.9.0-jdk21-alpine AS build
WORKDIR /build
COPY . .
RUN gradle api:bootJar --no-daemon

FROM amazoncorretto:21-alpine
WORKDIR /app
COPY --from=build /build/apps/api/build/libs/api-0.0.1.jar /app/usermanagment-be.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "usermanagment-be.jar"]
FROM maven:3.8.4-openjdk-17 as build

COPY . /build-project
WORKDIR /build-project

RUN mvn clean package -DskipTests

FROM openjdk:17
EXPOSE 8080
COPY --from=build /build-project/target/hello-vault-spring.jar /app.jar
ENTRYPOINT ["java","-jar", "/app.jar"]

HEALTHCHECK \
    --start-period=1s \
    --interval=10s \
    --timeout=1s \
    --retries=30 \
        CMD curl --fail -s http://localhost:8080/healthcheck || exit 1

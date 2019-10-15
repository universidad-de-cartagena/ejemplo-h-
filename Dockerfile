FROM openjdk:8-slim-buster as builder

WORKDIR /app
COPY .mvn/ .mvn/
COPY pom.xml .
COPY mvnw .
COPY src/ src/
RUN chmod +x mvnw \
    && ./mvnw clean package -DskipTests --batch-mode

FROM openjdk:8u222-jre-slim-buster

COPY --from=builder /app/target/ejemplo-java-1.0.0-SNAPSHOT-runner.jar app.jar
EXPOSE 8080
## Add the wait script to the image
ADD https://github.com/ufoscout/docker-compose-wait/releases/download/2.6.0/wait /bin/wait
RUN chmod +x /bin/wait
ENV WAIT_HOSTS=database:3306
CMD /bin/wait && java -jar app.jar

FROM gradle:4.2-jdk8 as builder

COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle build

FROM openjdk:8-jre-alpine
EXPOSE 9090:8080
WORKDIR /app
COPY --from=builder /home/gradle/src/build/libs/*.jar app.jar

CMD java -jar app.jar
//FROM openjdk:8-jdk-alpine
//EXPOSE 8080:8080
//COPY /build/libs/*.jar /app.jar
//ENTRYPOINT ["java","-jar","/app.jar"]


FROM gradle:jdk8 as builder

COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle build

FROM openjdk:8-jre-alpine
EXPOSE 9090:8080
COPY --from=builder /home/gradle/src/lets-find-me-back/build/libs/*.jar /app/app.jar
WORKDIR /app
RUN jar -xvf app.jar
WORKDIR /app/lets-find-me-back
CMD bin/lets-find-me-back
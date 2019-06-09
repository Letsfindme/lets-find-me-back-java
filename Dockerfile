
FROM gradle:4.2-jdk8 as builder

COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle build

FROM openjdk:8-jre-alpine
MAINTAINER Noufal4me@gmail.com
VOLUME /tmp
EXPOSE 8080
WORKDIR /app
COPY --from=builder /home/gradle/src/build/libs/*.jar /app/app.jar

#CMD java -jar app.jar
ENTRYPOINT ["java","-jar","app.jar"]
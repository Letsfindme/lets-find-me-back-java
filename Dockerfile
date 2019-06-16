#FROM gradle:jdk8 as builder
#RUN echo 'I did choose gradle to build'
#
#COPY --chown=gradle:gradle . /home/gradle/src
#WORKDIR /home/gradle/src
#RUN gradle build

FROM openjdk:8-jre-alpine
MAINTAINER Noufal4me@gmail.com
#VOLUME /tmp
EXPOSE 8080
#WORKDIR /app
#COPY --from=builder /home/gradle/src/build/libs/*.jar /app/app.jar
COPY /build/libs/*.jar app.jar
ENV doneVar="Hey!! it's finally done"
#CMD java -jar app.jar
ENTRYPOINT ["java","-jar","app.jar"]
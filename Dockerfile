FROM openjdk:8-jdk-alpine
MAINTAINER int28h
VOLUME /tmp
COPY target/scheduler-*.jar app.jar
COPY test.db test.db
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"] 

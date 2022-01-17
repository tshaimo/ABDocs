FROM openjdk:8u111-jdk-alpine
VOLUME /tmp

ARG VERSION
ADD target/Abdocs-$VERSION.jar app.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
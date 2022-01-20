FROM docker-virtual.artifactory.dev.africanbank.net:80/openjdk:8u111-jdk-alpine
VOLUME /tmp
#EXPOSE 8080 80 443 8443 22
ARG VERSION
ADD target/Abdocs-$VERSION.jar app.jar
#ADD target/Abdocs-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]

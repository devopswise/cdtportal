FROM openjdk:8-jdk
VOLUME /tmp
ARG JAR_FILE
COPY ./target/cdtportal-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
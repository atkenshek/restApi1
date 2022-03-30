FROM adoptopenjdk/openjdk11:alpine-jre
ADD target/rest-api-docker-0.0.1-SNAPSHOT.jar rest-api-docker.jar
ENTRYPOINT ["java","-jar","rest-api-docker.jar"]
EXPOSE 8080

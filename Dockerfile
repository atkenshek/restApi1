FROM adoptopenjdk/openjdk11:alpine-jre
ADD target/*.jar rest-api-docker.jar
ENTRYPOINT ["java","-jar","rest-api-docker.jar"]
EXPOSE 8080

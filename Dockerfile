FROM openjdk:21
VOLUME /tmp
EXPOSE 8080
ARG JAR_FILE=build/libs/members-0.0.1-SNAPSHOT.jar
ADD ${JAR_FILE} app/app.jar
ENTRYPOINT ["java","-jar","app/app.jar"]
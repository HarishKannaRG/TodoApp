FROM openjdk:21-ea
WORKDIR /app
COPY target/todo-app-0.0.1-SNAPSHOT.jar /app/todo-app.jar
EXPOSE 8080
CMD ["java","-jar","/app/todo-app.jar","--server.address=0.0.0.0"]
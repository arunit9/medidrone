FROM openjdk:8-jdk-alpine
ARG JAR_FILE=target/*.jar
ENV JAVA_TOOL_OPTIONS -agentlib:jdwp=transport=dt_socket,address=8000,server=y,suspend=n
COPY ${JAR_FILE} app.jar
RUN mkdir /image-dir
COPY image-dir/* /image-dir
ENTRYPOINT ["java","-jar","/app.jar"]
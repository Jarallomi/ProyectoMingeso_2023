FROM openjdk:17
ARG JAR_FILE=target/webApp_Jaramillo.jar
COPY ${JAR_FILE} webApp_Jaramillo.jar
EXPOSE 8086
ENTRYPOINT ["java","-jar","/webApp_Jaramillo.jar"]
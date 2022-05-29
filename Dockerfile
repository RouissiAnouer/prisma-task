FROM openjdk:11.0.2-jdk-oraclelinux7
COPY target/*.jar app.jar 
ENTRYPOINT ["java","-jar","app.jar"]
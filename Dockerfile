FROM openjdk:17
EXPOSE 9090
ADD target/device-managment-images.jar device-managment-images.jar
ENTRYPOINT ["java","-jar","/device-managment-images.jar"]
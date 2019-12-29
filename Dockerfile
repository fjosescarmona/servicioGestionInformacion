FROM openjdk:8
VOLUME /tmp
ADD ./target/servicioGestionInformacion-0.0.1-SNAPSHOT.jar gestion.jar
ENTRYPOINT ["java","-jar","/gestion.jar"]
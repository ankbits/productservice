FROM adoptopenjdk:11.0.4_11-jre-hotspot
COPY build/libs/product-service-0.0.1-SNAPSHOT.jar /app/
COPY startApp.sh /
RUN chmod +x startApp.sh
WORKDIR /app
EXPOSE 8080
ENTRYPOINT ["/startApp.sh"]

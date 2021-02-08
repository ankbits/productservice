FROM openjdk:8-jre-slim
ENV WIREMOCK_VERSION 2.18.0
RUN mkdir -p /wiremock/
RUN apt-get update
RUN apt-get install -y ca-certificates apt-transport-https wget curl
RUN wget https://repo1.maven.org/maven2/com/github/tomakehurst/wiremock-standalone/$WIREMOCK_VERSION/wiremock-standalone-$WIREMOCK_VERSION.jar \
 -O /wiremock/wiremock-jre8-standalone.jar
COPY /__files /wiremock/__files
COPY /mappings /wiremock/mappings
COPY /wiremockStart.sh /wiremock/
RUN chmod +x /wiremock/wiremockStart.sh
RUN ls -la /wiremock/*
WORKDIR /wiremock
EXPOSE 8085
ENTRYPOINT ["/bin/sh", "-c", "/wiremock/wiremockStart.sh", "--"]
#!/bin/bash

JAVA_OPTS="\
 $JAVA_OPTS \
 -XX:+UseG1GC \
 -Xms1024m \
 -Xmx1536m \
 -XX:MaxMetaspaceSize=256m \
 -Dallow.unsigned.sdk.extension.jars=true"

java ${JAVA_OPTS} -jar product-service-0.0.1-SNAPSHOT.jar

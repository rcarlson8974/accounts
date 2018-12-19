FROM anapsix/alpine-java
RUN apk add --update curl
ADD docker/logback.xml logback.xml
ADD docker/app.jar app.jar
CMD ["/bin/bash", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -Dlogging.config=/logback.xml -jar /app.jar"]


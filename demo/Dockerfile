FROM walkingdevs/jvm

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]

RUN ln -s /.distribution/apache-tomee/logs /logs

ADD target/app.jar /
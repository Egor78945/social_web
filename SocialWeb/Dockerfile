FROM openjdk:21

VOLUME /tmp

ADD out/artifacts/SocialWeb_jar/SocialWeb.jar app.jar

RUN sh -c 'touch /app'

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app.jar"]
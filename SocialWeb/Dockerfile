FROM openjdk:21

ADD out/artifacts/SocialWeb_jar/SocialWeb.jar sw.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "sw.jar"]
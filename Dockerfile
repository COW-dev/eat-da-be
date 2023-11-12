FROM amazoncorretto:17-alpine

CMD ["./gradlew", "clean", "build"]
EXPOSE 8080

ARG JAR_FILE=build/libs/*.jar
ARG PROFILE=prod
ARG DB_URL
ARG DB_USERNAME
ARG DB_PASSWORD

COPY ${JAR_FILE} app.jar

ENV PROFILE=${PROFILE}
ENV DB_URL=${DB_URL}
ENV DB_USERNAME=${DB_USERNAME}
ENV DB_PASSWORD=${DB_PASSWORD}

ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=${PROFILE}", "-Djava.security.egd=file:/dev/./urandom", "/app.jar"]

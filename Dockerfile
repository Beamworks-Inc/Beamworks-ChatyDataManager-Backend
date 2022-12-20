FROM openjdk:11-jre-slim as was

ARG DEPENDENCY=./build/dependency
COPY ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY ${DEPENDENCY}/META-INF /app/META-INF
COPY ${DEPENDENCY}/BOOT-INF/classes /app
ARG PROFILE
ENV PROFILE $PROFILE
ENTRYPOINT java -cp app:app/lib/* com.example.springkotlintemplate.SpringKotlinTemplateApplicationKt --spring.profiles.active=$PROFILE
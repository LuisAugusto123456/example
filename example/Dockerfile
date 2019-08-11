FROM openjdk:8-jdk-alpine
LABEL maintainer="administrador@indigital.pe"
# setting microservice user
ENV USER=microservice
ENV UID=10500
ENV GID=10600
ENV TZ=America/Lima
# install timezone
RUN apk add --update tzdata && rm -rf /var/cache/apk/*
RUN addgroup --gid "$GID" "$USER" \
    && adduser \
    --disabled-password \
    --gecos "" \
    --ingroup "$USER" \
    --uid "$UID" \
    "$USER"
# setting pre-build directories
RUN mkdir -p /apps/config && \
    mkdir -p /var/log/us && \
    chown -R $UID:$GID /apps && \
    chown -R $UID:$GID /var/log/us
# setting application configuration
COPY target/application.jar /apps/application.jar
USER $USER
# setting enn variables
WORKDIR /apps

# run microservice cmd    
CMD ["java","-jar","application.jar"]
FROM vertx/vertx3

ARG uid=65534
ARG gid=65534
ARG user=nobody
ARG group=nogroup
ARG JAR_NAME=eucalyptus-xls-1.0.0.jar

ENV VERTICLE_NAME com.tekhne.eucalyptus.xls.verticles.XlsBootstrap
ENV VERTICLE_HOME /usr/verticles
ENV port=8080

EXPOSE $port

COPY build/install/eucalyptus-xls/lib $VERTICLE_HOME/
COPY build/resources/main/configuration.json $VERTICLE_HOME/

ENV CLASSPATH $VERTICLE_HOME/*
#USER $user

WORKDIR $VERTICLE_HOME
ENTRYPOINT ["sh", "-c"]
CMD ["exec vertx run $VERTICLE_NAME -conf $VERTICLE_HOME/configuration.json"]

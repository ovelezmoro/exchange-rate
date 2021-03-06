FROM adoptopenjdk/openjdk11:alpine-jre

ARG APP_NAME="exchange-rate"
ARG APP_VERSION="0.0.1"
ARG JAR_FILE="./target/*.jar"

RUN apk add --no-cache tzdata
ENV TZ='America/Lima'
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

RUN apk --update add fontconfig ttf-dejavu

#Agregamos Certificado
#COPY gdig2.crt $JAVA_HOME/lib/security
#RUN \
#    cd $JAVA_HOME/lib/security \
#    && keytool -import -keystore cacerts -storepass changeit -noprompt -trustcacerts -importcert -alias "Go Daddy Secure Certificate Authority - G2" -file gdig2.crt

WORKDIR /

#Copiamos el jar en el directorio de trabajo
RUN mkdir application && chmod 777 application
COPY ${JAR_FILE} /application/app.jar
WORKDIR /application

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "-Xms1024M", "-Xmx2048M", "app.jar"]
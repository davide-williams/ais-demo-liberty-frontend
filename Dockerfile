FROM icr.io/appcafe/open-liberty:23.0.0.10-full-java8-ibmjava-ubi as builder

FROM builder as build1

COPY --chown=1001:0 /config/server.xml /config/server.xml

#RUN features.sh

COPY --chown=1001:0 /cert/key.p12 /config/resources/security/key.p12

COPY --chown=1001:0 /front-end-service/target/ais-demo-frontend-app-*.war /config/apps/ais-demo-app.war

COPY --chown=1001:0 /cert/keystore.xml /config/configDropins/defaults/keystore.xml 

RUN configure.sh

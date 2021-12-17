FROM registry.gitlab.com/armdev/dockers/plume:8.0.4
MAINTAINER Davit Vasilyan
ENV SERVER_HOME /usr/local/tomee
COPY server.xml ${SERVER_HOME}/conf/server.xml
COPY target/badges-front.war ${SERVER_HOME}/webapps/badges-front.war
ENTRYPOINT ${SERVER_HOME}/bin/catalina.sh run
EXPOSE 9096

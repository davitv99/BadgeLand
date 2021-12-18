FROM registry.gitlab.com/armdev/dockers/plume:8.0.4
MAINTAINER Davit Vasilyan
ENV SERVER_HOME /usr/local/tomee
COPY server.xml ${SERVER_HOME}/conf/server.xml
COPY target/badgeland.war ${SERVER_HOME}/webapps/badgeland.war
ENTRYPOINT ${SERVER_HOME}/bin/catalina.sh run
EXPOSE 9096

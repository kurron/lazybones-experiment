# start with a Ubuntu base box with Java 8 pre-installed
FROM kurron/docker-azul-jdk-8:latest

# set the author/maintainer
MAINTAINER Ron Kurr "kurr@kurron.org"

# the init service and other commands must be run as root user
USER root

# launch scripts that watchdog process expects
RUN mkdir /etc/service/microservice
ADD service.sh /etc/service/microservice/run
RUN chmod a+x /etc/service/microservice/run

# start the init service
ENTRYPOINT ["/sbin/my_init"]

# keep the stuff that changes last.  Helps with Docker layering.
EXPOSE ${insecurePort}
EXPOSE ${jmxPort}

LABEL org.kurron.built-on="${generated}" org.kurron.branch="${branch}" org.kurron.version="${version}"

COPY ${projectName}.jar /opt/${projectName}.jar

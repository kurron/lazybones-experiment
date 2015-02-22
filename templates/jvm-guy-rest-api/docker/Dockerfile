# I realize that executing each step independently is supposed to increase image size but I want readability in this case

# Trusty
FROM ubuntu:14.04

MAINTAINER Ron Kurr <kurr@jvmguy.com>

# Install JDK 8
RUN apt-get --quiet update
RUN apt-get --quiet --yes install wget
RUN apt-get clean
RUN wget --quiet --output-document=/jdk-8.tar.gz --no-check-certificate --no-cookies --header "Cookie: oraclelicense=accept-securebackup-cookie" http://download.oracle.com/otn-pub/java/jdk/8u31-b13/jdk-8u31-linux-x64.tar.gz
RUN mkdir -p /usr/lib/jvm
RUN tar --gunzip --extract --verbose --file /jdk-8.tar.gz --directory /usr/lib/jvm
RUN rm -f /jdk-8.tar.gz
RUN chown -R root:root /usr/lib/jvm

# cheat and set Redis host to a running instance.  Should really be running alongside a Redis container

# set the environment variables
ENV JDK_HOME /usr/lib/jvm/jdk1.8.0_31
ENV JAVA_HOME /usr/lib/jvm/jdk1.8.0_31
ENV PATH $PATH:$JAVA_HOME/bin

# copy the application jar file from the build output directory into the image
COPY artifacts/jvm-guy-rest-api.jar /opt/example/jvm-guy-rest-api.jar

# expose the port that the application will be listening on
EXPOSE 8080

# set the working directory to where the application jar file was copied
WORKDIR /opt/example

# start application - Example usage: docker run --detach --publish 8090:8080 --name new-container-instance msal/magni --spring.profiles.active=test
#                   - ENTRYPOINT was used rather than CMD so additional properties can be specified, e.g. the profile name
ENTRYPOINT ["java", "-Dserver.port=8080", "-jar", "jvm-guy-rest-api.jar"]

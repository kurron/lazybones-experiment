##################################################
# Builds a docker image with Magni pre-installed #
##################################################

# start with a Ubuntu base box with Java 8 pre-installed
FROM dockerfile/java:oracle-java8

# set the author/maintainer
MAINTAINER Matthew Salvatore "msalvatore@transparent.com"

# update sources
RUN apt-get update

# copy the magni jar file from the build output directory into the image
COPY artifacts/magni.jar /opt/magni/magni.jar

# expose the port that magni will be listening on
EXPOSE 8080

# set the working directory to where the magni jar file was copied
WORKDIR /opt/magni

# start magni - Example usage: docker run --detach --publish 8090:8080 --name new-container-instance msal/magni --spring.profiles.active=test
#             - ENTRYPOINT was used rather than CMD so additional properties can be specified, e.g. the profile name
ENTRYPOINT ["java", "-Dserver.port=8080", "-jar", "magni.jar"]

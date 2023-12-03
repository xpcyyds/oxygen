FROM mc/base-jre8
MAINTAINER 2536287545@qq.com
VOLUME /tmp
ARG JAR_FILE
ADD ${JAR_FILE} /home/app.jar
ENV JAVA_HOME="/usr/java/latest"
ENV PATH="/usr/java/latest/bin:${PATH}"
ENV DOCKER_VM_OPTIONS=""
ENV DOCKER_ARG_OPTIONS=""
ENV LANG en_GB.utf8
CMD /home/startup.sh
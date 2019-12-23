# Version 1.0.0
# @@占位符可以在 mvn package 过程中自动替换Maven的属性到 target/docker输出文件目录中
#FROM @docker.repostory@/java-8-alpine
FROM livingobjects/jre8
MAINTAINER Nicemorning "oypebook@outlook.com"
VOLUME /tmp

# add project jar file into docker
ADD eureka-server.jar app.jar

ADD boot.sh boot.sh
RUN sh -c 'chmod +x /boot.sh'
RUN sh -c 'touch /app.jar'
ENV JAVA_OPTS=""
ENTRYPOINT [ "sh", "-c", "/boot.sh" ]

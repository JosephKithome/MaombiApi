FROM openjdk8

RUN yum install telnet -y
RUN yum install nano -y
RUN yum install bash-completion bash-completion-extras -y

COPY /build/libs/jblog-0.0.1-SNAPSHOT.jar /opt
RUN cp /usr/share/zoneinfo/Africa/Nairobi /etc/localtime

CMD ["java", "-jar", "/opt/jblog-0.0.1-SNAPSHOT.jar"]
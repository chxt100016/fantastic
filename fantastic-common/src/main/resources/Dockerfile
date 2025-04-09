FROM openjdk:8
VOLUME /tmp
RUN echo 'Asia/Shanghai' > /etc/timezone
COPY *.jar /root/app.jar
EXPOSE 9999
ENTRYPOINT ["java", "-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5005", "-jar", "/root/app.jar", "--spring.profiles.active=prod"]
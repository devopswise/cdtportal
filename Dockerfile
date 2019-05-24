FROM openjdk:8-jdk
VOLUME /tmp

ENV DOCKERVERSION=18.03.1-ce
RUN curl -fsSLO https://download.docker.com/linux/static/stable/x86_64/docker-${DOCKERVERSION}.tgz \
  && tar xzvf docker-${DOCKERVERSION}.tgz --strip 1 \
                 -C /usr/local/bin docker/docker \
  && rm docker-${DOCKERVERSION}.tgz

RUN apt-get update -q \
	&& apt-get install -y -q --no-install-recommends curl ca-certificates \
	&& curl -o /usr/local/bin/docker-compose -L \
		"https://github.com/docker/compose/releases/download/1.15.0/docker-compose-Linux-x86_64" \
	&& chmod +x /usr/local/bin/docker-compose
	
ARG JAR_FILE
COPY ./misc/entrypoint.sh /entrypoint.sh
ENTRYPOINT ["/entrypoint.sh"]
RUN chmod +x /entrypoint.sh

COPY ./misc/start-ws /usr/local/bin/start-ws
RUN chmod +x /usr/local/bin/start-ws
CMD ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]

COPY ./target/cdtportal-0.0.1-SNAPSHOT.jar app.jar

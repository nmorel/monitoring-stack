# Basé sur https://github.com/tutumcloud/tutum-docker-influxdb
FROM ubuntu:14.04

RUN apt-get update && apt-get install -y curl && \
  rm -rf /var/lib/apt/lists/*

# Install InfluxDB
ENV INFLUXDB_VERSION 0.8.8
RUN curl -s -o /tmp/influxdb_latest_amd64.deb https://s3.amazonaws.com/influxdb/influxdb_${INFLUXDB_VERSION}_amd64.deb && \
  dpkg -i /tmp/influxdb_latest_amd64.deb && \
  rm /tmp/influxdb_latest_amd64.deb && \
  rm -rf /var/lib/apt/lists/*

ADD run.sh /run.sh
RUN chmod +x /*.sh

ENV PRE_CREATE_DB **None**
ENV SSL_SUPPORT **False**
ENV SSL_CERT **None**

# Admin server
EXPOSE 8083

# HTTP API
EXPOSE 8086

# HTTPS API
EXPOSE 8084

VOLUME ["/data"]

CMD ["/run.sh"]

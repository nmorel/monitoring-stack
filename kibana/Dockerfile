FROM ubuntu:14.04

RUN apt-get update && apt-get install -y curl && \
  rm -rf /var/lib/apt/lists/*

# Install Kibana.
ENV KIBANA_VERSION 4.0.1
RUN \
  cd / && \
  curl -s -o kibana.tar.gz https://download.elasticsearch.org/kibana/kibana/kibana-$KIBANA_VERSION-linux-x64.tar.gz && \
  tar xvzf kibana.tar.gz && \
  rm -f kibana.tar.gz && \
  mv /kibana-$KIBANA_VERSION-linux-x64 /kibana && \
  rm -rf /var/lib/apt/lists/*

# Configuration
RUN \
  sed -i 's/port: 5601/port: 80/' /kibana/config/kibana.yml && \
  sed -i 's/localhost:9200/elasticsearch:9200/' /kibana/config/kibana.yml

EXPOSE 80

CMD ["/kibana/bin/kibana"]

FROM dockerfile/java:oracle-java7

# Logstash
ENV LOGSTASH_VERSION 1.5.0.rc2
RUN \
  cd / && \
  curl -s -o logstash.tar.gz https://download.elasticsearch.org/logstash/logstash/logstash-$LOGSTASH_VERSION.tar.gz && \
  tar xvzf logstash.tar.gz && \
  rm -f logstash.tar.gz && \
  mv /logstash-$LOGSTASH_VERSION /logstash

  CMD ["/logstash/bin/logstash", "agent", "-f", "/data/logstash/logstash.conf"]
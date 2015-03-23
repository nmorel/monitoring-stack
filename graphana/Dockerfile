FROM ubuntu:14.04

# Install packages
RUN \
  apt-get update && \
  apt-get -y install software-properties-common && \
  add-apt-repository -y ppa:nginx/stable && \
  apt-get update && \
  apt-get install -y \
    nginx \
    curl && \
  rm -rf /var/lib/apt/lists/*

# Configure nginx
RUN \
  echo "\ndaemon off;" >> /etc/nginx/nginx.conf && \
  chown -R www-data:www-data /var/lib/nginx

# Download graphana
ENV GRAFANA_VERSION 1.9.1
RUN \
  cd / && \
  curl -s -o grafana.tar.gz http://grafanarel.s3.amazonaws.com/grafana-${GRAFANA_VERSION}.tar.gz && \
  tar xvzf grafana.tar.gz && \
  rm -f grafana.tar.gz && \
  rm -rf /var/www/html && \
  mv grafana-${GRAFANA_VERSION} /var/www/html

# Configure graphana
RUN ln -s /data/graphana/config.js /var/www/html/config.js

# Define working directory.
WORKDIR /etc/nginx

# Define default command.
CMD ["nginx"]

# Expose ports.
EXPOSE 80
EXPOSE 443

# Volume for config
VOLUME ["/data"]
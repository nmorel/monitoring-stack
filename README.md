##### Nettoyage
# Suppression des containers
sudo docker rm graphana kibana influxdb elasticsearch monitoring_data
# Suppression des images
sudo docker rmi monitoring/graphana monitoring/kibana monitoring/influxdb
# Suppression des données elasticsearch
sudo rm -r <data_folder>data/elasticsearch/data/ && \
sudo rm -r <data_folder>data/elasticsearch/log/ && \
sudo rm -r <data_folder>data/elasticsearch/plugins/
# Suppression des données InfluxDB
sudo rm <data_folder>data/influxdb/.pre_db_created && \
sudo rm -r <data_folder>data/influxdb/db/ && \
sudo rm -r <data_folder>data/influxdb/raft/ && \
sudo rm -r <data_folder>data/influxdb/wal/

##### data volume container
sudo docker create -v <data_folder>data:/data --name monitoring_data ubuntu:14.04

##### elasticsearch
# Ajout du plugin marvel
sudo docker run --rm --volumes-from monitoring_data dockerfile/elasticsearch /elasticsearch/bin/plugin -Des.config=/data/elasticsearch/elasticsearch.yml -i elasticsearch/marvel/latest
# Création du container
sudo docker create -p 9200:9200 -p 9300:9300 --volumes-from monitoring_data --name elasticsearch --hostname elasticsearch dockerfile/elasticsearch /elasticsearch/bin/elasticsearch -Des.config=/data/elasticsearch/elasticsearch.yml

##### InfluxDB
# Build de l'image
sudo docker build -t=monitoring/influxdb .
# Création du container
sudo docker create -p 8083:8083 -p 8084:8084 -p 8086:8086 --volumes-from monitoring_data --name=influxdb --hostname=influxdb -e PRE_CREATE_DB="monitoringdb" monitoring/influxdb

##### Kibana
# Build de l'image
sudo docker build -t=monitoring/kibana .
# Création du container
sudo docker create -p 8081:80 --link elasticsearch:elasticsearch --name kibana monitoring/kibana

##### Graphana
# Build de l'image
sudo docker build -t=monitoring/graphana .
# Création du container
sudo docker create -p 8082:80 --volumes-from monitoring_data --link elasticsearch:elasticsearch --link influxdb:influxdb --name graphana monitoring/graphana

##### Appli
# Build de l'image
sudo docker build -t=monitoring/appli .
# Création du container
sudo docker create -p 8080:8080 --volumes-from monitoring_data --link influxdb:influxdb --name appli monitoring/appli
sudo docker create -p 8080:8080 --volumes-from monitoring_data --name appli monitoring/appli

##### Logstash
# Build de l'image
sudo docker build -t=monitoring/logstash .
# Création du container
sudo docker create --volumes-from monitoring_data --link elasticsearch:elasticsearch --name logstash monitoring/logstash

##### Lancement
sudo docker start elasticsearch influxdb kibana graphana

##### Arrêt
sudo docker stop graphana kibana influxdb elasticsearch

#Rebalance

This service can make rebalance financial portofolio

####Собрать и зпустить сервис в docker контейнере
```
docker build . -t kopranovin/rebalance
docker run -d --rm -p 8080:8080 -e "PROFILE=default" kopranovin/rebalance

docker build . -t kopranovin/rebalance:<tagName>
docker tag kopranovin/rebalance:latest kopranovin/rebalance:0.0.1
```

####Запустить приложение в кластере kubernetes
```
sudo nano /etc/hosts
<yourLocalIp> rebalance.local.ru

kubectl create ns dev
helm install my-release rebalance --namespace dev
helm delete my-release --namespace dev

http://http://rebalance.local.ru/webjars/swagger-ui/index.html?configUrl=/v3/api-docs/swagger-config#/rebalancing-controller/rebalancing
```
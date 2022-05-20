#Rebalance

This service can make rebalance financial portofolio

####Собрать и зпустить сервис в docker контейнере
```
docker build . -t kopranovin/rebalance
docker run -d --rm -p 8080:8080 -e "PROFILE=default" kopranovin/rebalance
```
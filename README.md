# Rebalance

This service can make rebalance financial portofolio

#### Build and run app in docker container
```
docker build . -t kopranovin/rebalance
docker run -d --rm -p 8080:8080 -e "PROFILE=default" kopranovin/rebalance

docker build . -t kopranovin/rebalance:<tagName>
docker tag kopranovin/rebalance:latest kopranovin/rebalance:0.0.1
```

#### Run app in kubernetes cluster
```
sudo nano /etc/hosts
<yourLocalIp> rebalance.local.ru

kubectl create ns dev
helm install my-release rebalance --namespace dev
helm delete my-release --namespace dev

http://rebalance.local.ru/webjars/swagger-ui/index.html?configUrl=/v3/api-docs/swagger-config#/rebalancing-controller/rebalancing
```

#### Request example
After start app you can open web page this [link](http://localhost:8080/webjars/swagger-ui/index.html?configUrl=/v3/api-docs/swagger-config#/rebalancing-controller/rebalancing)

Request body
```json
{
  "positions": [
    {
      "instrumentId": "string",
      "ticker": "SBER",
      "amount": 0,
      "targetShare": 0.17,
      "forwardSplits": [
        0
      ]
    },
{
      "instrumentId": "string",
      "ticker": "X5",
      "amount": 0,
      "targetShare": 0.17,
      "forwardSplits": [
        0
      ]
    },
{
      "instrumentId": "string",
      "ticker": "NVTK",
      "amount": 0,
      "targetShare": 0.17,
      "forwardSplits": [
        0
      ]
    },
{
      "instrumentId": "string",
      "ticker": "HEAD",
      "amount": 0,
      "targetShare": 0.17,
      "forwardSplits": [
        0
      ]
    },
{
      "instrumentId": "string",
      "ticker": "MDMG",
      "amount": 0,
      "targetShare": 0.17,
      "forwardSplits": [
        0
      ]
    },
{
      "instrumentId": "string",
      "ticker": "LKOH",
      "amount": 0,
      "targetShare": 0.05,
      "forwardSplits": [
        0
      ]
    },
{
      "instrumentId": "string",
      "ticker": "PLZL",
      "amount": 0,
      "targetShare": 0.05,
      "forwardSplits": [
        0
      ]
    },
{
      "instrumentId": "string",
      "ticker": "CHMF",
      "amount": 0,
      "targetShare": 0.025,
      "forwardSplits": [
        0
      ]
    },
{
      "instrumentId": "string",
      "ticker": "NLMK",
      "amount": 0,
      "targetShare": 0.025,
      "forwardSplits": [
        0
      ]
    }
  ],
  "cashInSavings": 100000
}
```

Curl example
```text
curl -X POST "http://localhost:8080/" -H  "accept: */*" -H  "Content-Type: application/json" -d "{\"positions\":[{\"instrumentId\":\"string\",\"ticker\":\"SBER\",\"amount\":0,\"targetShare\":0.17,\"forwardSplits\":[0]},{\"instrumentId\":\"string\",\"ticker\":\"X5\",\"amount\":0,\"targetShare\":0.17,\"forwardSplits\":[0]},{\"instrumentId\":\"string\",\"ticker\":\"NVTK\",\"amount\":0,\"targetShare\":0.17,\"forwardSplits\":[0]},{\"instrumentId\":\"string\",\"ticker\":\"HEAD\",\"amount\":0,\"targetShare\":0.17,\"forwardSplits\":[0]},{\"instrumentId\":\"string\",\"ticker\":\"MDMG\",\"amount\":0,\"targetShare\":0.17,\"forwardSplits\":[0]},{\"instrumentId\":\"string\",\"ticker\":\"LKOH\",\"amount\":0,\"targetShare\":0.05,\"forwardSplits\":[0]},{\"instrumentId\":\"string\",\"ticker\":\"PLZL\",\"amount\":0,\"targetShare\":0.05,\"forwardSplits\":[0]},{\"instrumentId\":\"string\",\"ticker\":\"CHMF\",\"amount\":0,\"targetShare\":0.025,\"forwardSplits\":[0]},{\"instrumentId\":\"string\",\"ticker\":\"NLMK\",\"amount\":0,\"targetShare\":0.025,\"forwardSplits\":[0]}],\"cashInSavings\":100000}"
```
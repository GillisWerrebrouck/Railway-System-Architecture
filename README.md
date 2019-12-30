# Railway application

## Run all the services

```
bash build.sh && docker-compose up --build
```

To initialise a basic railway network with stations, use the following REST calls;
```
curl -X POST http://localhost:8080/network/init
curl -X POST http://localhost:8080/station/init
```

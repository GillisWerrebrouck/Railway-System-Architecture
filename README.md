# Railway System Architecture

## Run all the services

```
bash build.sh && docker-compose up --build
```

To initialise a basic railway network with stations, use the following REST calls (either both should be executed or none);
```
curl -X POST http://localhost:8080/station/init
curl -X POST http://localhost:8080/network/init
```

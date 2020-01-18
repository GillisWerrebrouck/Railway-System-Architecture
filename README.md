# Railway System Architecture

## Run all the services

### Run Docker setup

Execute the following command to build all microservices and start all docker containers.

```
bash build.sh && docker-compose up --build
```

To initialise a basic railway network with stations, use the following REST calls (either both should be executed or none);
```
curl -X POST http://localhost:8080/station/init
curl -X POST http://localhost:8080/network/init
```

### Run Kubernetes setup

```
kubectl create -f kubernetes-deployment.yaml
```

All services will start and all pods will be created. Execute the following command to check if all pods are running stable;

```
while true; do clear; kubectl get pods; sleep 3; done;
```

To access the frontend you should have portforwarding enabled for the frontend service, as well as for the route-db service and the apigateway service. The reason why the route-db service needs to be port forwarded is due to the fact that there is a canvas displaying all station nodes and route nodes on the frontend which uses a package that needs access to the Neo4j database.

If portforwarding is not an option, then try ssh tunneling (for all three services mentioned above) by using the following command as a template.

```
ssh -L <port>:<hosted_ip>:<port> -i '<ssl cert>' <username>@<ip> -oPort=22
-oProxyCommand="ssh -i <ssl cert> -oPort=22 <username>@<domain|url> -W %h:%p"
```

To initialise a basic railway network with stations, use the following REST calls (either both should be executed or none);

```
curl -X POST http://localhost:8080/station/init
curl -X POST http://localhost:8080/network/init
```

There is also a bash script `chaos.sh` in the root of this project that automatically kills services that aren't labelled as immune. This script has a default delay of 30 seconds. This can be changed by setting the environment variable DELAY to a certain value. Execute the following command to see all pods that are or aren't labelled immune.

```
kubectl --namespace default -l 'chaos=immune' get pods
kubectl --namespace default -l 'chaos!=immune' get pods
```
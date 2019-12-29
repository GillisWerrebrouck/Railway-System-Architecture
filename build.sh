#! /bin/bash

base="railway-app-"
declare -a services=(
	"api-gateway"
	"delay"
	"maintenance"
	"route-management"
	"staff"
	"station"
	"ticket-sale"
	"ticket-validation"
	"timetable"
	"train"
)

for s in "${services[@]}"
do
	cd $base$s
	chmod +x mvnw
	./mvnw package -DskipTests
	cd ..
done

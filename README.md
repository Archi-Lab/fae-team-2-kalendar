# Kalendar

Dies ist der Kalender des Systems _GPS-Schuhsohle_. Hier werden Kalender mit Zuordnung zu einer DVP verwaltet, die aus ein oder mehreren Kalendereinträgen bestehen können. Weitere Informationen zu Details, wie der _REST-API_ dem _Eventing_ und dem allgemeinen _Aufbau_ befinden sich im [Wiki](https://github.com/Archi-Lab/fae-team-2-kalendar/wiki).


## Prerequisites

* [Maven](https://maven.apache.org/install.html)
* [Docker](https://www.docker.com/)

## Getting Started

Mit den folgenden Schritten kann das Projekt aus dem Projektordner ausgeführt werden:

Projekt bauen:
```
mvn install -DskipTests=true
```

Docker Image bauen:
```
docker-compose build
```

Docker Container starten:
```
docker-compose up
```

Mehrere Kalender-Services starten:
```
docker-compose up --scale kalender=2
```

Belegter Port: 8022

## Acknowledgements
* Der Eventing Code wurde von [REWE Digital](https://github.com/rewe-digital/integration-patterns) übernommen. 
* Die EntityUUID4 Klasse wurde von [Team 1](https://github.com/Archi-Lab/fae-team-1) übernommen. 
* Das docker-compose File ist an das vom [sample-microservice](https://github.com/Archi-Lab/sample-microservice) angelehnt.

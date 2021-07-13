# lit 🔥

### Server
<hr/>
A server may be started running
```
mvn spring-boot:run
```

### Federation
<hr/>

A server may be started, with the objective to join an existing federation
```
mvn spring-boot:run -Dspring-boot.run.arguments="<known member> <something>"
```
where
- **known member** : which consists of the hostname and a port i.e. ```localhost:8080```
  - only http is supported as the protocol as of now
- **something** : is ignored but usually would instruct the server which port to start on, like this ```--server.port=8080```

The federation expects these two arguments, so that
```
mvn spring-boot:run -Dspring-boot.run.arguments="--server.port=8080"
```
still works.

#### Federation test setup

The following setup could be used for testing the federation.

**Server 1 :**
```
mvn spring-boot:run
```
Which is a regular server, hence it starts on port 8080.

**Server 2 :**
```
mvn spring-boot:run -Dspring-boot.run.arguments="localhost:8080 --server.port=8081"
```
Which connects to the server running on port 8080.

**Server 3 :** 
```
mvn spring-boot:run -Dspring-boot.run.arguments="localhost:8081 --server.port=8082"
```
Which connects to the server running on port 8081.
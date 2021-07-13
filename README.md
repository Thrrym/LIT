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

### Frontend
<hr/>
All files for the frontend are in folder `frontend/`. You may find a basic set of instructions in `frontend/README.md`.

#### Requirements
* [node version 14 LTS](https://nodejs.org/en/) was used during development and needs to be installed.

#### Production Instruction
* In folder `frontend/` run `npm install` to locally install required packages.
* In folder `frontend/` run `npm run build` to build the frontend server for production.
* Folder `dist/` will be created containing required Javascript, HTML and CSS files.
* To start the frontend server run: `node server.js 8080` with `8080` denoting the port.
  * File `frontend/server.js` configures an `express` server.
  * The server expects the associated backend server to run on the next port. Example: Frontend on port 8080 and backend on port 8081.
* The frontend is now accessible through a browser.

#### Development Instructions
* In folder `frontend/` run `npm install` to install locally the required development packages.
* Start the development server: `npm run serve`. Port 8080 or higher is selected.
* The development server is now reachable.
* Same port expectations as above: Run the backend server on the next port.


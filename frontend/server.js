// server.js
// CLI arguments: First argument frontend port.
let args = process.argv.slice(2);
if (args.length === 0) {
  console.log(
    "To start frontend server run 'node server.js 8080' Specifying the port of the frontend server.",
    "Expect the backend server to run on port 8081."
  );
  return;
}

// Where to reach the frontend server.
const hostname = "localhost";
const frontendPort = parseInt(args[0]);

// Load and use the same proxy as for the development server.
const express = require("express");
const { createProxyMiddleware } = require("http-proxy-middleware");

//let path = require("path");
//let serveStatic = require("serve-static");
let app = express();

// Setup the proxy: Forward backend requests from the frontend to the backend server.
const proxyOptions = {
  target: "http://localhost:8085/", // Is required! Router updates the target based on proxy handle.
  router: function (req) {
    // Update the target. Where does the request needs to be sent?
    // Decision based on the proxy handle. Example '/api8080'.
    console.log("originalUrl", req.originalUrl);
    const apiPort = req.originalUrl.split("/")[1].slice(3); // Get the port from the handle.
    console.log("apiPort", apiPort);
    const apiHost = req.headers.host.split(":")[0]; // Get the host. Should always be 'localhost'.
    console.log("apiHost", apiHost);
    const protocol = "http://"; // Set the protocol.
    console.log("router port", apiPort);
    // Construct the new target URL: Protocol, host and port.
    // Here we expect this to be always in the form: 'http://localhost:8080/'
    let newTarget = protocol + apiHost + ":" + apiPort + "/";
    console.log("newTarget", newTarget);
    return newTarget;
  },
  timeout: 6000, // Timeout (in millis) for incoming requests.
  ws: true, // Proxy websockets.
  secure: false, // Want to verify the SSL Certs.
  changeOrigin: true, // Changes the origin of the host header to the target URL.
  pathRewrite: function (path) {
    // Remove from the path the proxy handle, example '/api8080'. Uses a custom function.
    let newPath = path.split("/").slice(2).join("/");
    console.log("newPath", newPath);
    return newPath;
  },
};
const proxy = createProxyMiddleware(proxyOptions);
app.use("/api*", proxy); // Use of wildcard here required?

app.use(express.static(__dirname + "/dist"));

app.listen(frontendPort, hostname, () => {
  console.log(`Server running at http://${hostname}:${frontendPort}/`);
});

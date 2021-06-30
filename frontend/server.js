// server.js
// CLI arguments: First argument frontend port, second argument backend port.
let args = process.argv.slice(2);
let backendPort = args[1];

// Load and use the same proxy as for the development server.
const { createProxyMiddleware } = require("http-proxy-middleware");

let express = require("express");
//let path = require("path");
let serveStatic = require("serve-static");
let app = express();

// Where to reach the frontend server.
let hostname = "localhost";
let frontendPort = parseInt(args[0]);

// Setup the proxy: Forward backend requests from the frontend to the backend server.
app.use(
  "/api",
  createProxyMiddleware({
    target: "http://localhost:8081/", // Is required! Router updates the target based on proxy handle.
    router: function (req) {
      // Update the target. Where does the request needs to be sent?
      // Decision based on the proxy handle. Example '/api8080'.
      const apiPort = req.originalUrl.split("/")[1].slice(3); // Get the port from the handle.
      const apiHost = req.headers.host.split(":")[0]; // Get the host. Should always be 'localhost'.
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
      return newPath;
    },
  })
);
app.use(serveStatic(__dirname + "/dist"));

app.listen(frontendPort, hostname, () => {
  console.log(`Server running at http://${hostname}:${frontendPort}/`);
});
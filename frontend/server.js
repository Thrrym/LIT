// server.js
// CLI arguments: First argument frontend port, second argument backend port.
let args = process.argv.slice(2);
let backendPort = args[1];

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
    target: "http://localhost:" + backendPort + "/",
    changeOrigin: true,
    pathRewrite: {
      "^/api": "",
    },
  })
);
app.use(serveStatic(__dirname + "/dist"));

app.listen(frontendPort, hostname, () => {
  console.log(`Server running at http://${hostname}:${frontendPort}/`);
});

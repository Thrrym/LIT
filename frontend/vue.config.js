module.exports = {
    devServer: {
      proxy: {
        // For full options see https://github.com/chimurai/http-proxy-middleware#options
        '^/api8080': {
          target: 'http://127.0.0.1:8080/',
          timeout: 6000, // Timeout (in millis) for incoming requests.
          ws: true, // Proxy websockets.
          secure: false, // Want to verify the SSL Certs.
          changeOrigin: true, // Changes the origin of the host header to the target URL.
          pathRewrite: {
            '^/api8080': ''
          }
        },
        '^/api8081': {
          target: 'http://127.0.0.1:8081/',
          timeout: 6000, // Timeout (in millis) for incoming requests.
          ws: true, // Proxy websockets.
          secure: false, // Want to verify the SSL Certs.
          changeOrigin: true, // Changes the origin of the host header to the target URL.
          pathRewrite: {
            '^/api8081': ''
          }
        },
      }
    }
  }
<template>
  <div></div>
</template>

<script>
export default {
  name: "ServerComGetObject",

  data() {
    return {
      requestResponse: "",
    };
  },

  props: {},

  methods: {
    triggerGetObject: function (objectUrl) {
      // Get the Object from the backend based on a provided URL.

      // Maintaine reference to this component with `this` via a new reference.
      // Reason: Within httpRequest.onreadystatechange the reference changes to httpRequest.
      var component = this;

      // Create the HTTP Request. Uses xmlhttprequest npm package.
      var XMLHttpRequest = require("xmlhttprequest").XMLHttpRequest;
      var httpRequest = new XMLHttpRequest();

      // Set the HTTP Method. HTTP Request send via Proxy to backend server.
      let method = "GET";
      var apiUrl = this.getApiObjectUrl(objectUrl);
      console.log("Used Api", apiUrl);

      httpRequest.open(method, apiUrl, true);
      httpRequest.setRequestHeader(
        "Content-Type",
        "application/json;charset=UTF-8"
      );

      httpRequest.onreadystatechange = function () {
        // How to react on change of the HTTP request.
        if (httpRequest.readyState === httpRequest.DONE) {
          // HTTP request is finished.
          let status = httpRequest.status;
          if (status === 0 || (status >= 200 && status < 400)) {
            // The request has been completed successfully.
            component.requestResponse = httpRequest;
            // Trigger event.
            component.callbackResponse();
          } else {
            // Error handling.
            component.callbackError();
          }
        }
      };
      httpRequest.send(); // Send the HTTP request with no payload.
    },

    callbackResponse: function () {
      // Function triggered by the onreadystatechange from the HTTP request.
      // Emits event to parent component to pass result of the HTTP request back upstream.
      console.log(this.requestResponse);
      this.$emit("requestResponse", this.requestResponse);
    },

    callbackError: function () {
      // Function triggered by the onreadystatechange from the HTTP request.
      // Emits error to parent component back upstream.
      this.$emit("requestResponse", "error");
    },

    getApiObjectUrl: function (objectUrl) {
      // Construct correct API URL based on provided identifying URL of the object.
      // Slice the provided URL and construct a valid backend URL as used by the proxy.
      let objectUrlServer = objectUrl.split("/").slice(0, 3).join("/") + "/";
      console.log("objectUrlServer", objectUrlServer);
      // let backendPort = this.$store.state.backendUrl.split("/")[2].split(":")[1];
      if (objectUrlServer === this.$store.state.backendUrl) {
        let apiUrl = this.$store.state.proxyBackendUrl;
        console.log("otherProxyBackendUrl", apiUrl);
        return apiUrl + objectUrl.split("/").slice(1).slice(2).join("/");
      };
      let apiUrl = this.$store.state.otherProxyBackendUrl;
      console.log("otherProxyBackendUrl", apiUrl);
      return apiUrl + objectUrl.split("/").slice(1).slice(2).join("/");
    },
  },
};
</script>

<style></style>

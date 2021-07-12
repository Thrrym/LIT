<template>
  <div></div>
</template>

<script>
export default {
  name: "ServerComGetUserRelevantObjects",

  data() {
    return {
      requestResponse: "",
    };
  },

  props: {},

  methods: {
    triggerGetRelevantObjects: function () {
      // Get the current user.
      const currentUser = this.$store.getters.getUser;

      // Maintain reference to this component with `this` via a new reference.
      // Reason: Within httpRequest.onreadystatechange the reference changes to httpRequest.
      var component = this;

      // Create the HTTP Request. Uses xmlhttprequest npm package.
      var XMLHttpRequest = require("xmlhttprequest").XMLHttpRequest;
      var httpRequest = new XMLHttpRequest();

      // Set the HTTP Method. HTTP Request send via Proxy to backend server.
      let method = "GET";
      var apiUrl =
        this.$store.state.proxyBackendUrl + currentUser + "relevantobjects/";

      httpRequest.open(method, apiUrl, true);
      httpRequest.setRequestHeader(
        "Content-Type",
        "application/json;charset=UTF-8"
      );
      httpRequest.setRequestHeader("Authorization", this.$store.getters.getToken);

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
            component.requestResponse = httpRequest;
            component.callbackError();
          }
        }
      };
      httpRequest.send(); // Send the HTTP request with no payload.
    },

    callbackResponse: function () {
      // Function triggered by the onreadystatechange from the HTTP request.
      // Emits event to parent component to pass result of the HTTP request back upstream.
      this.$emit("requestResponse", this.requestResponse);
    },

    callbackError: function () {
      // Function triggered by the onreadystatechange from the HTTP request.
      // Emits error to parent component back upstream.
      this.$emit("requestError", this.requestResponse);
    },
  },
};
</script>

<style></style>

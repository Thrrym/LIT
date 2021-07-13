<template>
  <div></div>
</template>

<script>
export default {
  name: "ServerComRejectOffer",

  data() {
    return {
      requestResponse: "",
      objectRequestResponse: "",
      offerId: "",
      jsonPayload: {},
    };
  },

  methods: {
    trigger: function (offerId) {
      // Very first step: Get the original Post / object that needs to be liked.
      // This needs to be a separate function to ensure correct execution order. Reason AJAX stuff.
      this.offerId = offerId;

      // Get the current user and URL of backend.
      const backendUrl = this.$store.state.backendUrl;
      const currentUser = this.$store.getters.getUser;

      // Prepare content of the http request. Removes unused properties.
      this.prepareJson(
        backendUrl,
        currentUser,
      );

      // Maintain reference to this component with `this` via a new reference.
      // Reason: Within httpRequest.onreadystatechange the reference changes to httpRequest.
      const component = this;

      // Create the HTTP Request. Uses xmlhttprequest npm package.
      var XMLHttpRequest = require("xmlhttprequest").XMLHttpRequest;
      var httpRequest = new XMLHttpRequest();

      // Set the HTTP Method. HTTP Request send via Proxy to backend server.
      let method = "POST";
      var apiUrl = this.$store.state.proxyBackendUrl + currentUser + "outbox";

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
      console.log(this.jsonPayload);
      httpRequest.send(JSON.stringify(this.jsonPayload)); // Send the HTTP request with the JSON as payload.
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

    prepareJson: function (backendUrl, currentUser) {
      // Build the JSON payload for the Like activity.
      let jsonObject = {
        "@context": "https://www.w3.org/ns/activitystreams/",
        /*id: "https://example.net/~mallory/87374",*/
        type: "Reject",
        actor: backendUrl + currentUser,
        object: this.offerId,
        published: this.getCurrentTime(),
      };
      this.jsonPayload = jsonObject;
    },
    getCurrentTime: function () {
      // Return current time in standard ISO format.
      let d = new Date();
      return d.toISOString();
    },
  },
  computed: {
    getObjectId: function () {
      return this.objectId;
    },
  }
};
</script>

<style></style>

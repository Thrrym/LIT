<template>
  <div>
  </div>
</template>

<script>
//import ServerComGetObject from "@/components/ServerComGetObject.vue";

export default {
  name: "ServerComFollow",

  data() {
    return {
      requestResponse: "",
      jsonPayload: {},
    };
  },

  methods: {
    triggerFollow: function (userUrl) {
      this.userToFollow = userUrl;

      // Get the current user and URL of backend.
      const backendUrl = this.$store.state.backendUrl;
      const currentUser = this.$store.getters.getUser;

      // Prepare content of the http request. Removes unused properties.
      this.jsonPayload = this.prepareFollowJson(
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

    prepareFollowJson: function (backendUrl, currentUser) {
      // Prepare the json as http payload.
      // var objectJson = JSON.parse(this.objectRequestResponse.responseText); // Transform the base object from string to JSON.

      // Verify if the lit object is contained in parent json object. If so, extract object.
      // Reason Like maybe called on different URL types:
      // http://localhost:8080/testuser01/81b8fbd5-5afd-4468-921e-74718d4d439f or
      // http://localhost:8080/testuser01/bibtex_article/7640aa8a-96d2-4913-a36b-4c3eaef746f7
      /*if (Object.prototype.hasOwnProperty.call(objectJson, "object")) {
        objectJson = objectJson.object;
      }*/

      // Build the JSON payload for the Like activity.
      let jsonObject = {
        "@context": "https://www.w3.org/ns/activitystreams/",
        type: "Follow",
        to: this.userToFollow,
        actor: {
          type: "Person",
          name: backendUrl + currentUser,
        },
        object: this.userToFollow,
      };

      return jsonObject;
    },
  },
};
</script>

<style></style>

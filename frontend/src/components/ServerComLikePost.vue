<template>
  <div>
    <ServerComGetObject
      ref="object"
      v-on:requestResponse="setObjectRequestResponse"
    ></ServerComGetObject>
  </div>
</template>

<script>
import ServerComGetObject from "@/components/ServerComGetObject.vue";

export default {
  name: "ServerComPostLike",

  data() {
    return {
      requestResponse: "",
      objectRequestResponse: "",
    };
  },

  components: {
    ServerComGetObject,
  },

  props: {},

  methods: {
    triggerLikePost: function (objectToLikeUrl) {
      // Very first step: Get the original Post / object that needs to be liked.
      // This needs to be a separate function to ensure correct execution order. Reason AJAX stuff.
      this.getObject(objectToLikeUrl);
    },

    getObject: function (objectToLikeUrl) {
      // Second step.
      // Get the object from the backend. Reason: Object contains information needed for the like.
      this.$refs.object.triggerGetObject(objectToLikeUrl);
    },

    setObjectRequestResponse: function (object) {
      // Manage the return of the object by ServerComGetObject component.
      this.objectRequestResponse = object;
      console.log(this.objectRequestResponse)
      // Trigger the actual like.
      this.likePost();
    },

    likePost: function () {
      console.log(this.objectRequestResponse);
      if (this.objectRequestResponse === "error") {
        // Manage error if not possible to retrieve the object.
        // Propagate the error to parent component.
        this.callbackError();
        return;
      }

      // Get the current user and URL of backend.
      const backendUrl = this.$store.state.backendUrl;
      const currentUser = this.$store.getters.getUser;

      // Prepare content of the http request. Removes unused properties.
      var json = this.prepareLikeJson(
        backendUrl,
        currentUser,
        this.objectRequestResponse
      );

      // Maintain reference to this component with `this` via a new reference.
      // Reason: Within httpRequest.onreadystatechange the reference changes to httpRequest.
      var component = this;

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
            component.callbackError();
          }
        }
      };
      console.log(json);
      httpRequest.send(JSON.stringify(json)); // Send the HTTP request with the JSON as payload.
    },

    callbackResponse: function () {
      // Function triggered by the onreadystatechange from the HTTP request.
      // Emits event to parent component to pass result of the HTTP request back upstream.
      this.$emit("requestResponse", this.requestResponse);
    },

    callbackError: function () {
      // Function triggered by the onreadystatechange from the HTTP request.
      // Emits error to parent component back upstream.
      this.$emit("requestResponse", "error");
    },

    prepareLikeJson: function (backendUrl, currentUser) {
      // Prepare the json as http payload.
      var objectJson = JSON.parse(this.objectRequestResponse.responseText); // Transform the base object from string to JSON.

      // Verify if the lit object is contained in parent json object. If so, extract object.
      // Reason Like maybe called on different URL types:
      // http://localhost:8080/testuser01/81b8fbd5-5afd-4468-921e-74718d4d439f or
      // http://localhost:8080/testuser01/bibtex_article/7640aa8a-96d2-4913-a36b-4c3eaef746f7
      if (Object.prototype.hasOwnProperty.call(objectJson, "object")) {
        objectJson = objectJson.object;
      }

      // Build the JSON payload for the Like activity.
      let jsonObject = {
        "@context": "https://www.w3.org/ns/activitystreams/",
        type: "Like",
        actor: backendUrl + currentUser,
        to: objectJson.attributedTo,
        object: objectJson.id,
      };

      return jsonObject;
    },
  },
};
</script>

<style></style>

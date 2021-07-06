<template>
  <div>
  </div>
</template>

<script>
export default {
  name: "ServerComSignIn",
  data() {
    return {
    };
  },
  methods: {
    triggerSignIn: function (form) {
      // Very first step: Get the original Post / object that needs to be liked.
      // This needs to be a separate function to ensure correct execution order. Reason AJAX stuff.
      this.sendSignIn(form);
    },

    sendSignIn: function (form) {
      // Prepare content of the http request. Removes unused properties.
      const json = this.prepareSignInJson(form);

      // Maintain reference to this component with `this` via a new reference.
      // Reason: Within httpRequest.onreadystatechange the reference changes to httpRequest.
      const component = this;

      // Create the HTTP Request. Uses xmlhttprequest npm package.
      const XMLHttpRequest = require("xmlhttprequest").XMLHttpRequest;
      let httpRequest = new XMLHttpRequest();

      // Set the HTTP Method. HTTP Request send via Proxy to backend server.
      let method = "POST";
      const apiUrl = this.$store.state.proxyBackendUrl + "signin";

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
            //component.requestResponse = httpRequest;
            // Trigger event.
            //console.log(httpRequest.responseText)
            component.callbackResponse(httpRequest);
          } else {
            component.callbackError(httpRequest);
          }
        }
      };
      console.log(json);
      httpRequest.send(JSON.stringify(json)); // Send the HTTP request with the JSON as payload.
    },

    callbackResponse: function (httpRequest) {
      // Function triggered by the onreadystatechange from the HTTP request.
      // Emits event to parent component to pass result of the HTTP request back upstream.
      //this.$emit("requestResponse", this.requestResponse);
      const message = JSON.parse(httpRequest.responseText);
      if (Object.prototype.hasOwnProperty.call(message, "accessToken")) {
        this.$emit("sign-in-success", message);
      } else {
        this.$emit("sign-in-success", message);
      }
    },
    callbackError: function (httpRequest) {
      // Function triggered by the onreadystatechange from the HTTP request.
      // Emits error to parent component back upstream.
      const message = JSON.parse(httpRequest.responseText);
      if (Object.prototype.hasOwnProperty.call(message, "error")) {
        this.$emit("sign-in-failure");
      }
    },

    prepareSignInJson: function (form) {
      // Prepare the json as http payload.
      //var objectJson = JSON.parse(this.objectRequestResponse.responseText); // Transform the base object from string to JSON.

      // Verify if the lit object is contained in parent json object. If so, extract object.
      // Reason Like maybe called on different URL types:
      // http://localhost:8080/testuser01/81b8fbd5-5afd-4468-921e-74718d4d439f or
      // http://localhost:8080/testuser01/bibtex_article/7640aa8a-96d2-4913-a36b-4c3eaef746f7
      //if (Object.prototype.hasOwnProperty.call(objectJson, "object")) {
      //  objectJson = objectJson.object;
      //}

      // Build the JSON payload for the Like activity.
      let jsonObject = {
        username: form.username,
        password: form.password,
      };

      return jsonObject;
    },
  },
};
</script>

<style></style>

<template>
  <div></div>
</template>

<script>
export default {
  name: "ServerComNewAuthor",

  data() {
    return {
      requestResponse: "",
      jsonPayLoad: "",
      newEntry: "",
    };
  },

  props: {},

  methods: {
    triggerServerComNewAuthor: function (newEntry) {
      this.newEntry = newEntry;

      // Get the current user and URL of backend.
      const backendUrl = this.getBackendUrl;
      const currentUser = this.getCurrentUser;

      // Prepare content of the http request. Removes unused properties.
      let cleanNewEntry = this.prepareNewEntry(this.newEntry);
      this.jsonPayLoad = this.prepareNewEntryJson(
        cleanNewEntry,
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
      //httpRequest.timeout = 4000;
      httpRequest.setRequestHeader(
        "Content-Type",
        "application/json;charset=UTF-8"
      );
      httpRequest.setRequestHeader(
        "Authorization",
        this.$store.getters.getToken
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

      httpRequest.onerror = function () {
        console.log("HTTP onerror");
      };

      //console.log(this.jsonPayLoad);
      httpRequest.send(JSON.stringify(this.jsonPayLoad)); // Send the HTTP request with the JSON as payload.
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
    prepareNewEntry: function (uncleanNewEntry) {
      // Prepare the JSON Object to POST new entry to backend.
      // 1. Remove unused properties.
      let simplifiedObject = new Object();
      console.log(uncleanNewEntry);
      for (let index = 0; index < uncleanNewEntry.length; index++) {
        const element = uncleanNewEntry[index];
        simplifiedObject[element.name] = element.content;
      }
      return simplifiedObject;
    },
    prepareNewEntryJson: function (simplifiedObject, backendUrl, currentUser) {
      // 1. Construct JSON object containing the info of the new entry.
      let url = backendUrl + currentUser + "outbox/";
      var jsonLitObject = {
        id: url + "article/" + "1/",
        attributedTo: backendUrl + currentUser,
      };
      for (let property in simplifiedObject) {
        jsonLitObject[property] = simplifiedObject[property];
      }
      jsonLitObject["type"] = "author"

      // 2. Construct the main JSON. Contains as object the new entry to lit.
      var jsonMainObject = {
        "@context": "https://www.w3.org/ns/activitystreams/",
        type: "Create",
        id: url + "1/",
        actor: backendUrl + currentUser,
        published: this.getCurrentTime(),
        cc: "",
        object: jsonLitObject,
      };
      return jsonMainObject;
    },
    getCurrentTime: function () {
      // Return current time in standard ISO format.
      let d = new Date();
      return d.toISOString();
    },
  },
  computed: {
    getCurrentUser: function () {
      return this.$store.getters.getUser;
    },
    getBackendUrl: function () {
      return this.$store.state.backendUrl;
    },
  },
};
</script>

<style></style>

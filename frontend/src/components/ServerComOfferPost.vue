<template>
  <div>
    <ServerComGetObject
      ref="object"
      v-on:requestResponse="setObjectRequestResponse"
      v-on:requestError="setError"
    ></ServerComGetObject>
  </div>
</template>

<script>
import ServerComGetObject from "@/components/ServerComGetObject.vue";

export default {
  name: "ServerComOfferPost",

  data() {
    return {
      requestResponse: "",
      objectRequestResponse: "",
      updatedEntry: "",
      cc: "",
      jsonPayLoad: "",
      objectOriginal: "",
    };
  },

  components: {
    ServerComGetObject,
  },

  methods: {
    triggerOfferPost: function (objectToPostUrl, updatedEntry, cc) {
      // Very first step: Get the original Post / object that needs to be updated.
      // This needs to be a separate function to ensure correct execution order. Reason: AJAX stuff.
      this.cc = cc;
      this.updatedEntry = updatedEntry;
      this.getObject(objectToPostUrl);
    },

    getObject: function (objectToLikeUrl) {
      // Second step.
      // Get the object from the backend. Reason: Object contains information needed for the like.
      this.$refs.object.triggerGetObject(objectToLikeUrl);
    },

    setObjectRequestResponse: function (object) {
      // Manage the return of the object by ServerComGetObject component.
      this.objectRequestResponse = object;
      this.objectOriginal = JSON.parse(this.objectRequestResponse.responseText);
      this.selectedType = this.objectOriginal.type;
      this.offerPost();
    },

    setError: function () {
      // Manage error if not possible to retrieve the object.
      // Propagate the error to parent component.
      this.callbackError();
    },

    offerPost: function () {
      //console.log(this.objectRequestResponse);

      // Prepare content of the http request. Removes unused properties.
      /*var json = this.prepareUpdateJson(
        backendUrl,
        currentUser,
        this.objectRequestResponse
      );*/
      this.prepareOfferJson();

      // Maintain reference to this component with `this` via a new reference.
      // Reason: Within httpRequest.onreadystatechange the reference changes to httpRequest.
      var component = this;

      // Create the HTTP Request. Uses xmlhttprequest npm package.
      var XMLHttpRequest = require("xmlhttprequest").XMLHttpRequest;
      var httpRequest = new XMLHttpRequest();

      // Set the HTTP Method. HTTP Request send via Proxy to backend server.
      let method = "POST";
      var apiUrl = this.$store.state.proxyBackendUrl + this.getCurrentUser + "outbox";

      httpRequest.open(method, apiUrl, true);
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
      this.$emit("requestResponse", "error");
    },

    prepareOfferJson: function () {
      // Get the current user and URL of backend.
      //const backendUrl = this.getBackendUrl;
      //const currentUser = this.getCurrentUser;

      let simplifiedObject = this.prepareNewEntry(this.selectedType, this.updatedEntry);
      // 1. Construct JSON object containing the info of the new entry.
      console.log(simplifiedObject);
      //let url = backendUrl + currentUser + "outbox/";
      let jsonLitObject = {
        id: this.objectOriginal.id,
        attributedTo: this.objectOriginal.attributedTo,
      };
      for (let property in simplifiedObject) {
        if (property === "type") {
          jsonLitObject[property] = simplifiedObject[property];
        } else {
          jsonLitObject[property] = simplifiedObject[property];
        }
      }
      for (const property in this.objectOriginal) {
        if (!Object.prototype.hasOwnProperty.call(jsonLitObject, property)) {
          jsonLitObject[property] = this.objectOriginal[property];
        }
      }
      //console.log(jsonLitObject);

      // 2. Construct the main JSON. Contains as object the updated entry to lit.
      let jsonMainObject = {
        "@context": "https://www.w3.org/ns/activitystreams/",
        type: "Offer",
        published: this.getCurrentTime(),
        object: jsonLitObject,
      };
      this.jsonPayLoad = jsonMainObject;
    },

    getCurrentTime: function () {
      // Return current time in standard ISO format.
      let d = new Date();
      return d.toISOString();
    },

    getCc: function (backendUrl, currentUser, cc) {
      if (cc === "") {
        return ""; // Here you may specify to send Create to follower. But this is not implemented on the backend side.
      }
      return [cc];
    },
    prepareNewEntry: function (selectedType, uncleanNewEntry) {
      // Prepare the JSON Object to POST new entry to backend.
      // 1. Remove unused properties.
      let simplifiedObject = new Object();
      console.log(uncleanNewEntry)
      for (let index = 0; index < uncleanNewEntry.length; index++) {
        const element = uncleanNewEntry[index];
        simplifiedObject[element.name] = element.content;
      };
      simplifiedObject["type"] = selectedType;
      return simplifiedObject;
    },
  },
  computed: {
    getBackendUrl: function () {
      return this.$store.state.backendUrl;
    },
    getCurrentUser: function () {
      return this.$store.getters.getUser;
    },
  },
};
</script>

<style></style>

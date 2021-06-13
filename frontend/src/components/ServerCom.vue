<template>
  <div></div>
</template>

<script>
import { getUserUrl, getApiUrl, prepareNewEntry, prepareNewEntryJson } from "@/js_files/serverCom.js";

export default {
    name: "ServerCom",

    data() {
        return {
            requestResponse: "",
        }
    },

    props: {
    },

    methods: {
        triggerServerCom: function (newEntry, selectedType) {
            // Get the current user and URL of backend.
            const backendUrl = getUserUrl().backendUrl;
            const currentUser = getUserUrl().user;

            // Prepare content of the http request. Removes unused properties.
            console.log("ServerCom");
            console.log(newEntry);
            var cleanNewEntry = prepareNewEntry(selectedType, newEntry);
            var json = prepareNewEntryJson(cleanNewEntry, backendUrl, currentUser);

            // Maintaine reference to this component with `this` via a new reference.
            // Reason: Within httpRequest.onreadystatechange the reference changes to httpRequest.
            var component = this

            // Create the HTTP Request. Uses xmlhttprequest npm package.
            var XMLHttpRequest = require("xmlhttprequest").XMLHttpRequest;
            var httpRequest = new XMLHttpRequest();

            // Set the HTTP Method. HTTP Request send via Proxy to backend server.
            let method = "POST";
            var apiUrl = getApiUrl() + currentUser + "outbox";

            httpRequest.open(method, apiUrl, true);
            //httpRequest.timeout = 4000;
            httpRequest.setRequestHeader("Content-Type", "application/json;charset=UTF-8");

            httpRequest.onreadystatechange = function () {
                // How to react on change of the HTTP request.
                if (httpRequest.readyState === httpRequest.DONE) {
                    // HTTP request is finished.
                    let status = httpRequest.status;
                    if ((status === 0 || (status >= 200 && status < 400))) {
                        // The request has been completed successfully.
                        component.requestResponse = httpRequest;
                        // Trigger event.
                        component.callbackResponse();
                    } else {
                        component.callbackError();
                    }
                }
            }
            console.log(json)
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
    },
}
</script>

<style>
</style>
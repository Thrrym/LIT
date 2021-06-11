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
        selectedType: {
            required: true,
        },
        newEntry: {
            required: true,
        }
    },

    methods: {
        triggerServerCom: function () {
            // Get the current user and URL of backend.
            const backendUrl = getUserUrl().backendUrl;
            const currentUser = getUserUrl().user;

            // Prepare content of the http request. Removes unused properties.
            var newEntry = prepareNewEntry(this.selectedType, this.newEntry);
            var json = prepareNewEntryJson(newEntry, backendUrl, currentUser);

            // Maintaine reference to component with this via a new reference.
            var component = this

            // Create the HTTP Request. Uses xmlhttprequest npm package.
            var XMLHttpRequest = require("xmlhttprequest").XMLHttpRequest;
            var httpRequest = new XMLHttpRequest();

            console.log(json)

            // Set the HTTP Method. HTTP Request send via Proxy to backend server.
            let method = "POST";
            var apiUrl = getApiUrl() + currentUser + "outbox";

            httpRequest.open(method, apiUrl, true);
            //httpRequest.timeout = 4000;
            httpRequest.setRequestHeader("Content-Type", "application/json;charset=UTF-8");

            httpRequest.onreadystatechange = function () {
                console.log("readystatechange" + httpRequest.readyState)
                if (httpRequest.readyState === httpRequest.DONE) {
                    let status = httpRequest.status;
                    if ((status === 0 || (status >= 200 && status < 400))) {
                        // The request has been completed successfully
                        console.log(httpRequest.responseText);
                        component.requestResponse = httpRequest;
                        component.callbackResponse();
                        
                    } else {
                        console.log("error");
                    }
                }
            }
            httpRequest.send(JSON.stringify(json));
        },
    

        callbackResponse: function () {
            console.log("callbackResponse")
            console.log(this.requestResponse);
            this.$emit("requestResponse", this.requestResponse);
        },
    },
}
</script>

<style>

</style>
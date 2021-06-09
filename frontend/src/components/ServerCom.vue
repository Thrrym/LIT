<template>
  <div></div>
</template>

<script>
export default {
    name: "ServerCom",
    data() {
        return {

        }
    },
    props: {
        requestType: {
            required: true,
        },
        requestContent: {
            required: true,
        }
    },
    methods: {
        triggerServerCom: function () {
            // this.HtmlRequest();
            if (this.requestType === "newEntry") {
                this.postNewEntry();
            }
            return;
        },
        postNewEntry: function () {
            // Create the HTTP Request. Uses xmlhttprequest npm package.
            var XMLHttpRequest = require("xmlhttprequest").XMLHttpRequest;
            var httpRequest = new XMLHttpRequest();

            // How to handle state changes of the request.
            httpRequest.onreadystatechange = function(){
                
                console.log(httpRequest.status);
                console.log(httpRequest.responseText)
            };
            
            // Send what where:
            let method = "POST";
            let url = this.$backendUrl + this.$currentUser + "/" + "outbox";
            
            console.log(url)

            httpRequest.open(method, url, true);
            httpRequest.setRequestHeader("Content-Type", "application/json;charset=UTF-8");

            var jsonObject = {
                "id": url + "/article" + "/1",
                "attributedTo": this.$backendUrl + this.$currentUser,
            };
            for (const property in this.requestContent) {
                jsonObject[property] = this.requestContent[property];
            };

            var jsonMain = {
                "@context": "https://www.w3.org/ns/activitystreams",
                "type": "Create",
                "id": url + "/1",
                "actor": this.$backendUrl + this.$currentUser,
                "object": jsonObject
            };

            console.log(jsonMain)

            //httpRequest.send();
        },
        HtmlRequest: function () {
            return;
        },
        
    },
}
</script>

<style>

</style>
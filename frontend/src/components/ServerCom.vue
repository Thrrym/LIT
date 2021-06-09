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

            var json = {
                "@context": "https://www.w3.org/ns/activitystreams",
                "type": "Create",
                "id": url + "/1",
                "actor": this.$backendUrl + this.$currentUser,
                "object": {
                    "id": url + "article" + "/1",
                    "attributedTo": this.$backendUrl + this.$currentUser,

                    "type": "bibtex_article",
                    "author": "S.K. Bhaumik, R. Rangaraju, M.A. Venkataswamy, T.A. Bhaskaran, M.A. Parameswara",
                    "title": "Fatigue fracture of crankshaft of an aircraft engine",
                    "journal": "Engineering Failure Analysis",
                    "year": "2002",
                    "volume": "9"
                },
            }

            console.log(json)

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
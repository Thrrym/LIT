function getUserUrl() {
    // Change current and URL of backend here.
    return {
        user: "testuser01/",
        backendUrl: "http://localhost:8080/",
    };
}

function getApiUrl() {
    // Return the proxy URL.
    return "http://localhost:8081/api/";
}

function postNewEntry(selectedType, uncleanNewEntry) {
    // Get the current user and URL of backend.
    const backendUrl = getUserUrl().backendUrl;
    const currentUser = getUserUrl().user;

    // Prepare content of the http request. Removes unused properties.
    var newEntry = prepareNewEntry(selectedType, uncleanNewEntry);
    var json = prepareNewEntryJson(newEntry, backendUrl, currentUser);

    // Create the HTTP Request. Uses xmlhttprequest npm package.
    var XMLHttpRequest = require("xmlhttprequest").XMLHttpRequest;
    var httpRequest = new XMLHttpRequest();

    //console.log(json);
    // How to handle state changes of the request.
    // httpRequest.onreadystatechange = function(){
    //     if ((this.readyState == 4 && this.status == 201)) {
    //         console.log("Hier" + httpRequest);
    //         console.log(httpRequest.responseText);
    //     }
    // };

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
                // callback(httpRequest);
            } else {
                console.log("error");
            }
        }
    }

    httpRequest.send(JSON.stringify(json));
    //console.log(httpRequest.readyState);
    // while (httpRequest.readyState !== XMLHttpRequest.DONE) {
    //     console.log("wait");
    // }
    //return httpRequest;
    //return httpRequest;
}

// function callback(httpRequest) {
//     console.log(httpRequest);
//     this.requestResponse = httpRequest;
// }

function prepareNewEntry(selectedType, uncleanNewEntry) {
    // Prepare the JSON Object to POST new entry to backend.
    // 1. Remove unused properties.
    let simplifiedObject = new Object();
    for (let index = 0; index < uncleanNewEntry.length; index++) {
        const element = uncleanNewEntry[index];
        simplifiedObject[element.name] = element.content;
    };
    simplifiedObject["type"] = selectedType;
    return simplifiedObject;
}
    

function prepareNewEntryJson(simplifiedObject, backendUrl, currentUser) {
    // 1. Construct JSON object containing the info of the new entry.
    let url = backendUrl + currentUser + "outbox/";
    var jsonLitObject = {
        "id": url + "article/" + "1/",
        "attributedTo": backendUrl + currentUser,
    };
    for (let property in simplifiedObject) {
        if (property === "type") {
            jsonLitObject[property] = "bibtex_" + simplifiedObject[property];
        }
        else {
            jsonLitObject[property] = simplifiedObject[property];
        }
    };

    // 2. Construct the main JSON. Contains as object the new entry to lit.
    var jsonMainObject = {
        "@context": "https://www.w3.org/ns/activitystreams/",
        "type": "Create",
        "id": url + "1/",
        "actor": backendUrl + currentUser,
        "published": getCurrentTime(),
        "cc": [backendUrl + currentUser + "follower/", backendUrl + "testuser02/"],
        "object": jsonLitObject
    };
    return jsonMainObject
}

function getCurrentTime() {
    // Return current time in standard ISO format.
    let d = new Date();
    return d.toISOString()
}

export { getUserUrl, getApiUrl, postNewEntry, prepareNewEntryJson, prepareNewEntry }
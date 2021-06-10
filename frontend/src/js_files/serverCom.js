function postNewEntry(selectedType, uncleanNewEntry) {
    const backendUrl = "http://localhost:8080/";
    const currentUser = 'testuser01';

    // Prepare content of the http request. Removes unused properties.
    var newEntry = prepareNewEntry(selectedType, uncleanNewEntry);
    var json = prepareNewEntryJson(newEntry, backendUrl, currentUser);

    // Create the HTTP Request. Uses xmlhttprequest npm package.
    var XMLHttpRequest = require("xmlhttprequest").XMLHttpRequest;
    var httpRequest = new XMLHttpRequest();
    //console.log(json);
    // How to handle state changes of the request.
    httpRequest.onreadystatechange = function(){
        if ((this.readyState == 4 && this.status == 201)) {
            console.log("Hier" + httpRequest);
            console.log(httpRequest.responseText);
        }
    };
    
    // Send what where:
    let method = "POST";
    //let url = backendUrl + currentUser + "/" + "outbox";
    
    console.log(json)

    var apiUrl = "http://localhost:8081/" + "api/" + currentUser + "/" + "outbox";
    console.log(apiUrl)
    httpRequest.open(method, apiUrl, true);
    httpRequest.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
    httpRequest.send(JSON.stringify(json));
    return httpRequest;
}

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
    let url = backendUrl + currentUser + "/" + "outbox";
    var jsonLitObject = {
        "id": url + "/article" + "/1",
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

    // 2. Construct the containing JSON Object.
    var jsonMainObject = {
        "@context": "https://www.w3.org/ns/activitystreams",
        "type": "Create",
        "id": url + "/1",
        "actor": backendUrl + currentUser,
        "published": "2015-02-10T15:04:55Z",
        "cc": [backendUrl + currentUser + "/follower", backendUrl + "testuser02/"],
        "object": jsonLitObject
    };
    return jsonMainObject
}

function test() {
    console.log("Test");
}

export {postNewEntry, test}
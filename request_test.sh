#!/bin/bash

postInbox() {
    port_from=$1
    port_to=$2
    curl -i -H "Content-Type: application/json"  -d "{  \"@context\": \"https://www.w3.org/ns/activitystreams\", \
                                                        \"type\": \"Create\", \
                                                        \"id\": \"https://social.example/testuser01/posts/a29a6843-9feb-4c74-a7f7-081b9c9201d3\", \
                                                        \"to\": [ \"http://localhost:$port_to/testuser02\" ], \
                                                        \"actor\": \"http://localhost:$port_from/testuser01/\",  \
                                                        \"object\": { \"type\": \"Note\", \
                                                                        \"id\": \"https://social.example/alyssa/posts/49e2d03d-b53a-4c4c-a95c-94a6abf45a19\", \
                                                                        \"attributedTo\": \"http://localhost:$port_from/testuser01/\", \
                                                                        \"to\": [ \"http://localhost:$port_to/testuser02/\" ], \
                                                                        \"content\": \"Say, did you finish reading that book I lent you?\" \
                                                                        } }" -X POST "http://localhost:$port_to/testuser02/inbox"
}

postOutbox() {
    port_from=$1
    port_to=$2
    curl -i -H "Content-Type: application/json"  -d "{  \"@context\": \"https://www.w3.org/ns/activitystreams\", \
                                                        \"type\": \"Create\", \
                                                        \"id\": \"https://social.example/testuser01/posts/a29a6843-9feb-4c74-a7f7-081b9c9201d3\", \
                                                        \"to\": [ \"http://localhost:$port_to/testuser02\" ], \
                                                        \"actor\": \"http://localhost:$port_from/testuser01/\",  \
                                                        \"object\": { \"type\": \"Note\", \
                                                                        \"id\": \"https://social.example/alyssa/posts/49e2d03d-b53a-4c4c-a95c-94a6abf45a19\", \
                                                                        \"attributedTo\": \"http://localhost:$port_from/testuser01/\", \
                                                                        \"to\": [ \"http://localhost:$port_to/testuser02/\" ], \
                                                                        \"content\": \"Say, did you finish reading that book I lent you?\" \
                                                                        } }" -X POST "http://localhost:$port_from/testuser01/outbox"
}

getInbox() {
    port_from = $1
    curl -i -H "Content-Type: application/json" -X GET "http://localhost:$port_from/testuser01/inbox"
}

# main
port_from=8080
port_to=8080

if [ $# == "2" ] 
then
    port_from=$1
elif [ $# == "3" ] 
then
    port_to=$2
else 
    echo "continue with default ports $port_from $port_to"
fi

postOutbox $port_from $port_to
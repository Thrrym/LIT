#!/bin/bash

curl -i -H "Content-Type: application/json"  -d "{  \"type\": \"Create\", \
                                                    \"actor\": \"val\",  \
                                                    \"object\": \"val\", \
                                                    \"target\": \"val\", \
                                                    \"result\": \"val\", \
                                                    \"origin\": \"val\", \
                                                    \"instrument\": \"val\"}" -X POST "http://localhost:8080/john/inbox"
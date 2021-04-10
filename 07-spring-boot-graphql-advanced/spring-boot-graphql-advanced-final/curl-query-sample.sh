#!/bin/bash

curl -X POST 'http://localhost:4000/graphql' -H 'Content-Type: application/json' -d '{"query":"{routes{id}}"}'
curl -X POST 'http://localhost:4000/graphql' -H 'Content-Type: application/json' -d '{"query":"{routes{id flights{id}}}"}'
curl -X POST 'http://localhost:4000/graphql' -H 'Content-Type: application/json' -d '{"query":"{routes{id flights{id route{id}}}}"}'
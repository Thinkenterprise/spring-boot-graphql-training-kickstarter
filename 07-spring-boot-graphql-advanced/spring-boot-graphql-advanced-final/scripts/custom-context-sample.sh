#!/bin/bash
curl -X POST 'http://localhost:4000/graphql' -H 'Content-Type: application/json' -H 'user-id: GraphQL Training' -d '{"query":"{routes{id}}"}'

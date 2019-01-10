Simple study about contract tests between consumers and providers using Pact and Pact Broker.

Scenario:
1) There is an `api-in-go` which exposes two endpoints:
-- `/users/:id`
-- `/items/:sku`
2) There is a `frontend-client` that fetches from `api-in-go` using `/users/:id` endpoint
3) There is a `ruby-service` that fetches from `api-in-go` using `/item/:sku` endpoint
4) There is a `pact-broker` that will receive the contracts published by `frontend-client` and `ruby-service`, will provide to `api-in-go` so the API can test if the interactions are still valid or not and will show the current integration status

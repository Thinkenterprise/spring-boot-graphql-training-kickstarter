# GraphQL Query Language

## Start GraphQL Backend 

```
java -jar xyz
```

## Test Tools 

You can use **Postman**, **Playground** or the Command Line Shell like **bash**

Playground is provided by the GraphQL Backend over 

```
localhost:4000/playground 
```

### All Route 
```
 query allRoutes {
  routes(page: 0, size: 3) {
    id
    flightNumber
    isDisabled
  }
}
```
### All Route with Variables

```
query variable($page: Int = 0, $size: Int = 3) {
  routes(page: $page, size:$size) {
    id
    flightNumber
    isDisabled 
  }
}

```

### Three Queries in one Request 

```
query variable($page: Int = 0, $size: Int = 3) {
  first: routes(page: $page, size: $size) {
    id
    flightNumber
    isDisabled
  }
  second: routes(page: $page, size: $size) {
    id
    flightNumber
  }
  third: routes(page: $page, size: $size) {
    id
    flightNumber
    flights {
      id
    }
  }
}

```

### Three Queries in one Request using Fragments 

```
query variableThreeTimesFragment($page: Int = 0, $size: Int = 3) {
  first: routes(page: $page, size: $size) {
   ...routeDetails
    isDisabled
  }
  second: routes(page: $page, size: $size) {
    ...routeDetails
  }
  third: routes(page: $page, size: $size) {
    ...routeDetails
    flights {
      id
    }
  }
}

fragment routeDetails on Route {
  id
  flightNumber
}

```

## One concrete Route 

```
query single {
  route(flightNumber: "LH7902") {
    id
    flightNumber
    isDisabled
  }
}
```

## Create a new Route  

```
mutation createRoute {
  createRoute(flightNumber: "LH410") {
    id
    flightNumber
  }
}
```

## Subscriptions 

```
subscription {
  registerRouteCreated {
    id
    flightNumber
  }
}
```

## Introspection 

```
query {
  __type(name: "Route") {
    name
    fields {
      name
    }
  }
}
```






# GraphQL Query Language

The structure of the **GraphQL schema** is described below.

## Queries

### Query Routes 
```
query routes {
  routes {
    id
    flightNumber
    isDisabled
  }
}
```

### Query Routes with Pageination
```
query routes {
  routes {
    id
    flightNumber
    isDisabled
  }
}
```

### Query One Routes 
```
query oneRoute {
  route(flightNumber: "LH7902") {
    id
    flightNumber
    isDisabled
  }
}
```

### Query One Routes with Alias 
```
query oneRoutewithAlias {
  route(flightNumber: "LH7902") {
    id
    flightNumber
    alias: isDisabled
  }
}
```




### Query Parallel 

It is possible to execute one or more different queries. To access the right queries each query get a name ``name:<Query>``.

```
query parallel {
  FirstRoute: route(flightNumber: "LH7902") {
    id
    flightNumber
    isDisabled
  }
  SecondRoute: route(flightNumber: "LH1602") {
    id
  }
}
```

### Query with Variable 

**Variables** will be defined and used by ``$``. 

```
query variable($flightNumber: String = "LH7902") {
  route(flightNumber: $flightNumber) {
    id
    flightNumber
    isDisabled
  }
  SecondRoute: route(flightNumber: "LH1602") {
    id
    flightNumber
  }
}
```

### Query with Fragemnts  

**Fragments** will be defined by ``fragment`` followed by the fragmentation name and  ``on`` closed with the definition of the fragment. 


query fragement($flightNumber: String = "LH7902") {
  route(flightNumber: $flightNumber) {
    ...routeDetails
  }
  SecondRoute: route(flightNumber: "LH1602") {
    ...routeDetails
  }
}
fragment routeDetails on Route {
  id
  flightNumber
  isDisabled
}```

### Query Releation   

The next steps how three types of relations. 

From **Route** to **Flights** ``1:1..*``.

```
query fligths {
  routes {
    id
    isDisabled
    flights {
      id
      price
    }
  }
}
```

From **Flight** to **Route** ``1:1``.

```
query fligthsRoute {
  routes {
    id
    isDisabled
    flights {
      id
      price
      route {
        id
      }
    }
  }
}
```

From **Route** to **Route**.

```
query routeRoute {
  routes {
    id
    route {
      id
      flightNumber
      route {
        id
      }
    }
  }
}
```

### Query inheritance

Access different type of **Employee**. In this example we differentiate when it is a **Pilot**.

```
query {
  routes {
    id
    flights {
      id
      employees {
        id
        ... on Pilot {
          certificateNumber
        }
      }
    }
  }
}
```

## Query Mutations

### Mutation Create 

```
mutation createRoute {
  createRoute(flightNumber: "LH410") {
    id
    flightNumber
  }
}
```

### Mutation Update
```
mutation update {
  updateRoute(id: 101, departure: "BER") {
    id
    departure
  }
}
```

### Mutation Update with Input Object 

```
mutation {
  updateRouteWithRouteInput(
    id: 102
    routeInput: { departure: "BER", destination: "MUC" }
  ) {
    id
    flightNumber
    departure
    destination
  }
}

```

### Mutation Delete 

```
mutation delete {
  deleteRoute(id: 103)
}
```

## Query Subscriptions 

### Register on Create New Route   

```
mutation delete {
  deleteRoute(id: 103)
}
```
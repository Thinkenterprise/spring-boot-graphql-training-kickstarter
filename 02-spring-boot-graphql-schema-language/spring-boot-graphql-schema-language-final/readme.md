# GraphQL Schema Language

## Add Route Object 

```
type Route {
    id: ID!
    flightNumber: String!
    departure: String
    destination: String
    isDisabled: Boolean	
    flights: [Flight!]	
}
```

## Add Flight  Object 

```
type Flight {
    id: ID!
    price: Float!
    discount: Float!
    route: Route!
    date: LocalDate
}
```

## Add Custom Scalar

```
scalar LocalDate
```

## Add Operations  
```
schema {
	query: Query	
	mutation: Mutation	
}
```

## Add Query Operations   

```
type Query { 
	routes(page: Int = 0, size: Int = 1): [Route]
}
```

## Add Mutation Operations   

```
type Mutation {
	createRoute(flightNumber: String!): Route
}
```

## Add Deprecated Directives 


```
type Route {
   	departure: String @deprecated(reason: "Use `newField`.")
}
```

## Add Documentation 
We can document our model with ``#``. 

```
**#** Route documentation First Line 
**#** Route documentation Second Line
type Route {
    **#** id documentation 
	id: ID!
	
}
```
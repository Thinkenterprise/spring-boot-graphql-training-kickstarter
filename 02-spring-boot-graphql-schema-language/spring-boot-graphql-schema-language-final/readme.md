# GraphQL Schema Language

The structure of the **GraphQL schema** is described below.

## Queries

### Add Query Schema 
```
schema {
	query: Query	
}
```

### Add Query Object Type
The **Query** Object Type defines the **Root Queries**. 
```
type Query { 
	routes(page: Int = 0, size: Int = 1): [Route]
	route(flightNumber: String!): Route
}
```

Note the **parameters** for example ``page``, **scalars** for example ``Int`` and the **Nullable** expression  ``page``.  


### Add Route Object Type
The **Route** Object Type defines the Object itself.

```
type Route {
    id: ID!
    flightNumber: String!
    departure: String
    destination: String
    isDisabled: Boolean	
}
```

Note the **global object identifier** ``id: ID!``. Its like a string but signals that it is more technical and not human readable.

### Add hierarchical Connection 

Add hierarchical Connection to **Route**.

```
route: Route	
```

### Add a one-to-many Connection 

Add a one-to-many  Connection to another Object **Flight**.

```
flights: [Flight!]	
```

Note a **List** defined by  ``[]``

### Add Flight Object Type

The **Flight** Object defines the Object itself.

```
type Flight {
    id: ID!
    price: Float!
    discount: Float!
}
```

### Add a back connection 

Add a back connection to **Route**.

```
route: Route!
```

### Add Custome Scalar  

Define the **Custom Scalar**
```
scalar LocalDate
```

Use the new defined **Custom Scalar** in **Flight**. 

```
date: LocalDate!
```

### Add a one-to-many Connection 

Add a one-to-many  Connection to another Object **Employee**.

```
employees: [Employee!]	
```

### Add Employee Interface Type

The **Employee** Interface Type defines the Object itself by an ``interface`` , because we have different types of an **Employee**. **Interfaces** and **Unions** are two options to model different types. Both option have some advantages and disadvantages.  

```
interface Employee {
	id: ID!
	staffNumber: String!
	lastName: String! 
	firstName: String! 
	
}
```


### Add a Enumeration 

Add a Enumeration to the Employee Object Type.
To mark the different roles we introduce an **Enumeration**

```
role: EmployeRole!
```

The Enumeration will be defined by the ``enum``. 

```
enum EmployeRole {
	PILOT
	ATTENDANT
}
```


### Add Pilot Object Type 

One implementation of the **Employee** is the **Pilot**.

```
type Pilot implements Employee {
	id: ID!
	staffNumber: String!
	lastName: String! 
	firstName: String! 
	role: EmployeRole!
	certificateNumber: String 
}
```

### Add Attendant Object Type 

The other implementation of the **Employee** is the **Attendant**.

```
type Attendant implements Employee {
	id: ID!
	staffNumber: String!
	lastName: String! 
	firstName: String! 
	role: EmployeRole!
	rank: Int 
}
```


## Mutation

### Add Mutation Schema 
```
schema {
	query: Query
	mutation: Mutation	
}
```

### Add Mutation Object Type
The **Mutation** Object Type defines the **Root Mutations**. 
```
type Mutation {
	createRoute(flightNumber: String!): Route
	deleteRoute(id: ID!): Boolean
	updateRoute(id: ID!, departure: String!): Route
	updateRouteWithRouteInput(id: ID!, routeInput: RouteInput) : Route
}
```

### Add Input Type 
**Input Types** allow several fields to be passed as one parameter at the same time.

```
input RouteInput {
	departure: String
	destination: String
}
```


## Subcriptions

### Add Subscription Schema 
```
schema {
	query: Query
	mutation: Mutation
	subscription: Subscription	
}
```

### Add Subscription Object Type
The **Subscription** Object Type defines the **Root Subscription**. 
```
type Subscription {
    registerRouteCreated: Route 
}
```

## Add Documentation 
We can document our model with ``#``. 

**#** Route documentation First Line 
**#** Route documentation Second Line
type Route {
    **#** id documentation 
	id: ID!
	
}

## Add Directives 

We can use the standard directives ``skip``, ``deprecated`` etc. 

```
type Route {
   	departure: String @deprecated(reason: "Use `newField`.")
}
```

Or we can define **Custom Directives**

```
directive @uppercase on FIELD_DEFINITION
```

and use the Custom Directives on type, fields etc. 

```
type Route {
	flightNumber: String! @uppercase
}
```
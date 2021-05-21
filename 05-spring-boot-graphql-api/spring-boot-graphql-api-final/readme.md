# GraphQL API Exercise 


## Database 

The data are stored in an H2 database. All libraries, configuration, scripts and repositories are already available.
You can find it in the starter project. 


## Add Query Resolvers 

### Add Root Query Resolvers 

We have only one **Root Query Resolvers** with name ``RootQueryResolver``.

Here the code snippet  ``RootQueryResolver`` for your implementation.  

``` java 
@Component
public class RootQueryResolver implements GraphQLQueryResolver {

	protected static Logger log = LoggerFactory.getLogger(RootQueryResolver.class);

	private RouteRepository routeRepository;

	@Autowired
	public RootQueryResolver(RouteRepository routeRepository) {
		this.routeRepository = routeRepository;
	}

	public List<Route> routes(int page, int size) {

		Pageable pageable = PageRequest.of(page, size);

		Page<Route> pageResult = routeRepository.findAll(pageable);
		return pageResult.toList();
	}
}

```

### Add Property Query Resolver 

We have two **Property Query Resolvers**. One for **Route** ``RouteQueryResolver`` an one for the object **Flight** ``FLightQueryResolver``. 

Here the code snippet  ``RouteQueryResolver`` for your implementation.  

``` java 
@Component
public class RouteQueryResolver implements GraphQLResolver<Route> {

	
    private FlightRepository flightRepository;
    private RouteRepository routeRepository;

    @Autowired
    public RouteQueryResolver(RouteRepository routeRepository,
    					      FlightRepository flightRepository) {
    	this.routeRepository=routeRepository;
        this.flightRepository=flightRepository;
    }

    public List<Flight> flights(Route route) {
    	return flightRepository.findByRoute(route);	
    }

    public Route route(Route route) {
    	return routeRepository.findByRoute(route);
    }

}

```

Here the code snippet  ``FlightQueryResolver`` for your implementation.  

``` java 
@Component
public class FlightQueryResolver implements GraphQLResolver<Flight> {

	
    private EmployeeRepository employeeRepository;
    private RouteRepository routeRepository;
    private DiscountService discountService;
   
    @Autowired
    public FlightQueryResolver(RouteRepository routeRepository,
    						   EmployeeRepository employeeRepository,
    						   DiscountService discountService) {
        this.employeeRepository=employeeRepository;
        this.routeRepository=routeRepository;
        this.discountService=discountService;
        
    }

    public List<Employee> employees(Flight flight) {
        return employeeRepository.findByFlight(flight);
    }
    
    public Route route(Flight flight) {
    	return routeRepository.findById(flight.getRoute().getId()).get();
    }
    
    public Float discount(Flight flight) {
		return discountService.getDiscount(flight.getId());
	}
   
}

```

### Add inheritance

Both implementations ``Pilot`` and ``Attendant`` of the interface ``Employee` still have to be registered in the GraphQL type system. This is possible via the **Spring Boot Configuration**. 

Here the code snippet  ``GraphQLConfiguration`` for your implementation.  

``` java 
@Configuration
public class GraphQLConfiguration {

	@Bean
	public SchemaParserDictionary schemaParserDictionary() {
		return new SchemaParserDictionary().add(Pilot.class).add(Attendant.class);
	}

}

```


## Add Mutation Resolvers 


### Add Root Mutation Resolver 

We have only one **Root Mutation Resolvers** ``RootMutationResolver`` which provides the root filed implementation for create, update and delete Routes.  

The class ``RouteSubscriptionNotifier`` be discussed later. Here the code snippet for ``RootMutationResolver`` for your implementation. 

**TODO: TRANSACTIONS IN GRAPHQL**

``` java 
@Component
@Transactional
public class RootMutationResolver implements GraphQLMutationResolver {
	

    private RouteRepository routeRepository;
    private RouteSubscriptionNotifier projectReactorRouteSubscriptionNotifier;


    @Autowired
    public RootMutationResolver(RouteRepository routeRepository, 
    							RouteSubscriptionNotifier projectReactorRouteSubscriptionNotifier) {
    	this.projectReactorRouteSubscriptionNotifier=projectReactorRouteSubscriptionNotifier;
        this.routeRepository=routeRepository;	 
    }
 
    public Route createRoute(String flightNumber) {
    	Route route = new Route(flightNumber);
        routeRepository.save(route);
        projectReactorRouteSubscriptionNotifier.emit(route);
        return route; 
    }    

}
```


## Add Subscription Resolvers 

### Add Root Subscription Resolver 

We have only one **Root Subscription Resolvers**  ``RootSubscriptionResolver``. 

Here the code snippet ``RootSubscriptionResolver`` for your implementation. 

``` java 
@Component
public class RootSubscriptionResolver implements GraphQLSubscriptionResolver {


	private RouteSubscriptionNotifier routeSubscriptionNotifier;

	@Autowired
	public RootSubscriptionResolver(RouteSubscriptionNotifier routeUpdatePublisher) {
		this.routeSubscriptionNotifier = routeUpdatePublisher;
	}

	public Publisher<Route> registerRouteCreated() {
		return routeSubscriptionNotifier.getPublisher();
	}

}
```


### Add Publisher Configuration 

To create a publisher ``Publisher<Route>``, we use the **RouteSubscriptionNotifier** class that is provided in the ``*.publisher`` package. So that the class can be used via the **Autowiring**, an instance must be created via the **Spring Boot Configuration**.

Here the code snippet ``GraphQLConfiguration`` for your implementation. 

``` java 
@Configuration
public class GraphQLConfiguration {

	@Bean
	public RouteSubscriptionNotifier projectReactorRouteSubscriptionNotifier() {
		return new ProjectReactorRouteSubscriptionNotifier();
	}

}
```

The instance of **RouteSubscriptionNotifier** was also used in the mutation resolver ``RootMutationResolver`` to send an event to the client when creating a route.

## Test 

Add a method to test **query all routes**. 

```  
@Test
public void assertThatRoutesWorks() throws IOException { 
    GraphQLResponse response  = graphQLTestTemplate.postForResource("routes.graphql");
    assertNotNull(response);
    assertTrue(response.isOk());
    assertEquals("101", response.get("$.data.routes[0].id"));
}
```

Add a file named ``routes.graphql`` which has the correct test query.


```  
query {
  routes {
    id
    flightNumber
  }
}
```



## Add Directive 

In the package ``*.directive`` you can see an implementation of a directive ``UppercaseDirective`` which implements an **Uppercase**. 

To use the directive implementation you have to register the directive in the GraphQL Schema. In Spring Boot you can do this by creating  a new SchemaDirective instance over the **Spring Boot configuration**. 

Here the code snippet ``GraphQLConfiguration`` for your implementation. 

``` java 
@Configuration
public class GraphQLConfiguration {

     @Bean
	public SchemaDirective myCustomDirective() {
	    return new SchemaDirective("uppercase", new UppercaseDirective());
	}
	

}
```

## Add Custom Scalar 

GraphQL provides a number of built‐in scalars In, Float, String, Boolean and ID, but type systems can add additional scalars with semantic meaning.

To implement your own scalar type you have to implement the GraphQL interface ``GraphQLScalarType`` and register your scalar implementation over the  


```
RuntimeWiring.newRuntimeWiring().scalar(<your scalar implementation>)
```

There are already a number of libraries that provide additional scalars.

**GraphQL Extended Data Types** Library supports Date, Time, DateTime, URL, Object, JSON, Numerics, Regex, Locale. 
- [GraphQL Extended Data Types](https://github.com/graphql-java/graphql-java-extended-scalars)


The library will be delivered with **Spring Boot GraphQL Starter** and over the Autoconfiguration the new types will be registered.

You have only to configure which type you want to use. 


```
graphql:
  extended-scalars: Date
```


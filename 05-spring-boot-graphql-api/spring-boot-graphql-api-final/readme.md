# GraphQL API Exercise 


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

	public Route route(String flightNumber) {
		return routeRepository.findByFlightNumber(flightNumber);
	}

	public List<Route> routes(int page, int size) {

		Pageable pageable = PageRequest.of(page, size);

		Page<Route> pageResult = routeRepository.findAll(pageable);
		return pageResult.toList();
	}
}

```

### Add Object Query Resolver 

We have two **Object Query Resolvers**. One for **Route** ``RouteQueryResolver`` an one for the object **Flight** ``FLightQueryResolver``. 

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

    public Route updateRoute(Long id, String departure) {
 		Route route = routeRepository.findById(id).get();
 		route.setDeparture(departure);
 		routeRepository.save(route);
 		return route;
 	}
    
    public Route updateRouteWithRouteInput(Long id, RouteInput routeInput) {
        Route route = routeRepository.findById(id).get();
        route.setDeparture(routeInput.getDeparture());
        route.setDestination(routeInput.getDestination());
        routeRepository.save(route);      
        return route;
    }
    
    public Boolean isDeleteRoute(Long id) {
        routeRepository.deleteById(id);
        return true;
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

**GraphQL Extended Data Types** Library supports Date Time, Object, JSON, Numerics, Regex, Locale. 
- [GraphQL Extended Data Types](https://github.com/graphql-java/graphql-java-extended-scalars)


**Alexey Zhokhov** Library supports all data types defined in **ISO 8601**, **RFC 3339** that are supported from Java 8 onwards. In addition, the library provides a Spring Boot **Autoconfiguration** and **Properties** for the configuration.  

- [Alexey Zhokhov Github](https://github.com/donbeave/graphql-java-datetime)



### Add new Type Library

Here the code snippet from ``pom.xml`` for your implementation. 

```
<dependency>
  <groupId>com.zhokhov.graphql</groupId>
  <artifactId>graphql-datetime-spring-boot-starter</artifactId>
  <version>4.0.0</version>
</dependency>
```
# GraphQL Advanced API 

## Metrics  

Enable Metrics to monitor the Performance of your GraphQL API.

Add the **Actuator** Dependency.  

```  
<dependency>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-starter-actuator</artifactId>
</dependency>
```


Check the enabling of the Spring Boot Actuator **Endpoints**.

```  
## Actuator  
management:
  endpoints: 
    enabled-by-default: true
    web:
      exposure:
        include: "*"
```


Activate the GraphQL Actuator Metrics over the ``actuator-metrics`` property in the Spring Boot Property file. 

```  
servlet:
   actuator-metrics: true
```


If you execute a query you can see timing informations under the **metrics endpoint** ``/metrics``.  
**Attention: The metric is only created when a first GraphQL call is made!!**
The best IDE to check the actuator endpoint is postman or Mozilla Browser, because of the Hypermedia and JSON representation. 


```  
http://localhost:4000/actuator/metrics
```



## Asynchronous Calls 
Change the implementation of the synchronous call to a asynchronous call. 

```
@PreAuthorize("hasAuthority('SCOPE_read')")
	public CompletableFuture<Optional<Route>> route(String flightNumber) {
		return CompletableFuture.supplyAsync(() -> routeRepository.findByFlightNumber(flightNumber));
	}
```

## Data Loader 


Create your own **Batch Loader** which load a batch of discounts over the **Discount Service** in the package ``com.thinkenterprise.graphql.data``. 

```
public class DiscountBatchLoader implements BatchLoader<Long, Float>{
	
	protected static Logger log = LoggerFactory.getLogger(DiscountService.class);
	
	private DiscountService discountService;
	
	public DiscountBatchLoader(DiscountService discountService) {
		super();
		this.discountService = discountService;
	}

	@Override
	public CompletionStage<List<Float>> load(List<Long> ids) {	
		log.info("Discount for Flights " + ids );
		return CompletableFuture.supplyAsync(() -> discountService.getDiscountByIds(ids));
	}

}
```

Provide your Batch Loader over the ``CustomGraphQLServletContextBuilder``. Add the following implementation in our existing ``CustomGraphQLServletContextBuilder``.

```
public class CustomGraphQLServletContextBuilder implements GraphQLServletContextBuilder {

		
	@Autowired
	private DiscountService discountService;
	
	@Override
	public GraphQLContext build(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		
		String userId = httpServletRequest.getHeader("user-id");
	    defaultGraphQLServletContext = DefaultGraphQLServletContext.createServletContext()
				    											   .with(httpServletRequest)
				    											   .with(httpServletResponse)
				    											   .with(createDataLoaderRegisty())
				    											   .build();
		
		return new CustomGraphQLServletContext(userId, defaultGraphQLServletContext);	
	}

	public DataLoaderRegistry createDataLoaderRegisty() {
		return new DataLoaderRegistry().register("discount",
				DataLoader.newDataLoader(new DiscountBatchLoader(discountService)));
	}

}

```

Register the FLight Ids for the discount access in the Batch loader. Make the following changes in the existing ``FlightQueryResolver``. 

```
@Component
public class FlightQueryResolver implements GraphQLResolver<Flight> {

	
     CustomGraphQLServletContext customGraphQLServletContext = (CustomGraphQLServletContext) dataFetchingEnvironment.getContext();
    	DataLoader<Long, Float> discoutDataLoader = customGraphQLServletContext.getDataLoaderRegistry().getDataLoader("discount");
	return discoutDataLoader.load(flight.getId());
    
```

## Cache 

Each Data Loader has its own standard in memory cache and reads the data from this cache.
For each web request a Data Loader instance will be created. So that the cache lives only for the time of a web request. 
It is possible to change the default in memory cache with a global Cache, Redis for example. 

Implement your own Redis cache. There is already a finished implementation to be found under the package ``com.thinkenterprise.graphql.cache``. 
It is a simple spring bean with singleton scope and a map as cache store.  

 
```
public DataLoaderRegistry createDataLoaderRegisty() {
		DataLoaderOptions options = DataLoaderOptions.newOptions().setCacheMap(redisCache);
		
		return new DataLoaderRegistry().register("discount",
				DataLoader.newDataLoader(new DiscountBatchLoader(discountService), options));
	}
```

Test Cache. Execute a web request for two time. The second web request should use the cached value and reduce the time.  


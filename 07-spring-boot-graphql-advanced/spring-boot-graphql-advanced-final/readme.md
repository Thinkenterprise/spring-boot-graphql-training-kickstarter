# GraphQL Advanced API 

## Performance 

### Metrics  

Enable Metrics to monitor the Performance of your GraphQL API.

Add the **Actuator** Dependency.  

```  
<dependency>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-starter-actuator</artifactId>
</dependency>
```

Add property to the Spring Boot Property file. 

```  
servlet:
   actuator-metrics: true
```

Check the enableing of the Spring Boot Actuator **Endpoints**.

```  
## Actuator  
management:
  endpoints: 
    enabled-by-default: true
    web:
      exposure:
        include: "*"
```

If you execute a query you can see timing informations under the **metrics endpoint** ``http://localhost:4000/actuator/metrics/graphql.timer.query``.  
**Attention: The metric is only created when a first GraphQL call is made!!**

### Asynchronous Calls 
Change the implementation of the synchronous call to an asynchronous call an check the influence. 

```
public CompletableFuture<List<Route>> routes(int page, int size, DataFetchingEnvironment dataFetchingEnvironment) throws InterruptedException  {
		
	return CompletableFuture.supplyAsync(() ->  pageResult.toList()); 	
}
```

### Data Loader 
#### Add Data Loader 

Create your own **Batch Loader** which loads a batch of discounts over the **Discount Service** in the package ``com.thinkenterprise.graphql.data``. 

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

#### Provide your Batch Loader 

Add the following implementation in our existing ``CustomGraphQLServletContextBuilder``.

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

#### Register the Batch Loader Ids 

Make the following changes in the existing ``FlightQueryResolver``. 

```
@Component
public class FlightQueryResolver implements GraphQLResolver<Flight> {

	
      public CompletableFuture<Float> discount(Flight flight, DataFetchingEnvironment dataFetchingEnvironment) {

		DataLoader<Long, Float> discoutDataLoader = dataFetchingEnvironment.getDataLoader("discount");
		return discoutDataLoader.load(flight.getId());
	}
    
```

### Cache 

Each Data Loader has its own standard in memory cache and reads the data from this cache.
For each web request a Data Loader instance will be created. So that the cache lives only for the time of a web request. 
It is possible to change the default in memory cache with a global Cache.   

#### Implement your own Redis cache
In our example simulate a singleton Spring Bean a redis cache.
There is already a finished implementation to be found under the package ``com.thinkenterprise.graphql.cache``

#### Configure the new cache
 
```
public DataLoaderRegistry createDataLoaderRegisty() {
		DataLoaderOptions options = DataLoaderOptions.newOptions().setCacheMap(redisCache);
		
		return new DataLoaderRegistry().register("discount",
				DataLoader.newDataLoader(new DiscountBatchLoader(discountService), options));
}
```

Execute a web request for two times. The second web request should use the cached value an the performance should be increase. 




## DDOS 

GraphQL offers two concepts to protect the GraphQL interface from DDOS attacks. 




### Query Depth 

Set the **Query Depth** over the Spring Boot Application Properties. 

```
 servlet:
    maxQueryDepth: 3
```

Close Playground because the IDE polls the schema with introspection queries which longer then 13. 
You can test it over CURL. The Script is saved in the Script max-query-depth.sh

3 works 2 not!! 


After the test, set the property to 100. 

[Kickstart Issue 612](https://github.com/graphql-java-kickstart/graphql-spring-boot/issues/569)
[Kickstart Issue 612](https://github.com/graphql-java-kickstart/graphql-spring-boot/discussions/609)
[Kickstart Issue 612](https://github.com/graphql-java-kickstart/graphql-spring-boot/issues/612)
[Kickstart Issue 611](https://github.com/graphql-java-kickstart/graphql-spring-boot/issues/611)




### Query Complexity  


Set the **Query Depth** over the Spring Boot Application Properties. 

```
 servlet:
    maxQueryComplexity: 8
```
 
Close Playground because the IDE polls the schema with introspection queries which longer then 13. 
You can test it over CURL. The Script is saved in the Script max-query-complexity.sh

8 works 7 not!!

After the test, set the property to 100. 

[Kickstart Issue 278](https://github.com/graphql-java-kickstart/graphql-spring-boot/issues/278)


## Tracing & Metrics 


### Tracing 

Add property to the Spring Boot Property file.

```
tracingEnabled: true
```

If the Property is true the ``TracingInstrumentation`` will be enabled. 

Open the **Setting Panel** in Playground and enable the tracing information. 

```
"tracing.hideTracingResponse": false
```

If you execute a query you can see timing informations now. 






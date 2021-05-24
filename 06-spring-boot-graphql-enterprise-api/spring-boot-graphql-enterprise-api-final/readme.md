# GraphQL Enterprise API Exercise 


## Add Validation 

The validation is already defined in the schema 


```  
directive @Size(min : Int = 0, max : Int = 2147483647, message : String = "graphql.validation.Size.message")
on ARGUMENT_DEFINITION | INPUT_FIELD_DEFINITION

```

and the validation itself.


```  
route(flightNumber: String! @Size( min : 6, max : 6 )): Route

```

To support the directives first add the **Extended Validation** libraries. 

```  
<dependency>
	<groupId>com.graphql-java</groupId>
	<artifactId>graphql-java-extended-validation</artifactId>
	<version>16.0.0</version>
</dependency>

```

Register the validation Directives 

```
@Bean
	public ValidationSchemaWiring validationRules() {

		ValidationRules validationRules = ValidationRules.newValidationRules()
								 .onValidationErrorStrategy(OnValidationErrorStrategy.RETURN_NULL).build();
	
		return new ValidationSchemaWiring(validationRules);

	}
```

Test the validation. You can use the query saved under queries.  
                     

## Exceptions  

We choose the Spring based ``@ExceptionHandler`` implementation to handle the mapping between **Java Exceptions** and **GraphQL Erros**.  



To enable the feature we have to add some spring configuration in our property file.  

```  
graphql:
  servlet:
    exception-handlers-enabled: true 

```


Throw a business exception ``RouteNotFoundException`` if the route with the fligtNumber doesent exists. 

```  
public Route route(String flightNumber) {

	Optional<Route> route = routeRepository.findByFlightNumber(flightNumber);

	if (route.isEmpty())
		throw new RouteNotFoundException("Route with flightnumber " + flightNumber + " doesnt exists");
	else
		return route.get();

}
```

Implements a ``@ExceptionHandler`` method in our ``RootQueryResolver`` resolver which map the business exception to a GraphQL Error. 


```  
@ExceptionHandler(value = RouteNotFoundException.class)
	public GraphQLError exceptionHandler(RouteNotFoundException routeNotFoundException, ErrorContext error) {
		
		Map<String, Object> map = new HashMap<>();
		map.put("MyExtention", 2);
		
		return GraphqlErrorBuilder.newError().message(routeNotFoundException.getMessage())
											 .extensions(map)
				                             .location(error.getLocations().get(0))
				                             .path(error.getPath())
				                             .build();
		
	}

```

Test the implementation with the same query as before. 


## Context

The Context Classes `CustomGraphQLServletContextBuilder` and `CustomGraphQLServletContext`` are already exists. Understand an review the implementations. 


Create a instance of `CustomGraphQLServletContextBuilder`  over the Spring Boot Configuration. 

```
@Configuration
public class GraphQLConfiguration {

	@Bean
	public CustomGraphQLServletContextBuilder customGraphQLServletContextBuilder() {
		return new CustomGraphQLServletContextBuilder();
	}
	
}

```

Using the Custom Context. 

```
public List<Route> routes(int page, int size, DataFetchingEnvironment dataFetchingEnvironment)  {
			
		CustomGraphQLServletContext customGraphQLServletContext = (CustomGraphQLServletContext) dataFetchingEnvironment.getContext();
		log.info("Custom Context: " + customGraphQLServletContext.getUserId());
	
		Pageable pageable = PageRequest.of(page, size);

		Page<Route> pageResult = routeRepository.findAll(pageable);
		return pageResult.toList();
		
	}

```


Test the Custom Context. 

Execute the query 


```
query {
  routes {
    id
  }
}
```

over playground and add the header information in playground before executing.

```
{
	"user-id": "GraphQL Training"
}
```


## Security 


Check Spring Boot Security Dependency.  


To support an implement OAuth2/JWT you have to add the oauth2 resource server starter. 



```  
	<dependency>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starter-oauth2-resource-server</artifactId>
	</dependency>
```

To Enable the Security Configuration set only the profile ``security`` . 

```  
spring: 
   profiles:
    active:
    - security 	
```

Add the Public-Key Configuration. 

```  
security:
    oauth2:
      resourceserver:
        jwt: 
          public-key-location: public-key.txt 	
```
  
Add the ``@PreAuthorize`` Annotation on the ``route()`` method. 

```
@PreAuthorize("hasAuthority('SCOPE_read')")
	public Route route(String flightNumber) {
```


Test the security implementation. Execute the query 


```
query {
  route(flightNumber: "LH7902") {
    id
    flightNumber
    isDisabled
  }
}
```

over playground an add the header information in playground before executing.

```
{
	"Authorization": "Bearer eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJzY29wZSI6WyJyZWFkIl0sImV4cCI6MjE0NDA4NjQ0MCwiYXV0aG9yaXRpZXMiOlsiUk9MRV9VU0VSIl0sInVzZXJfbmFtZSI6InRvbSIsImp0aSI6ImM4N2Q5NTNjLTZlZDAtNGRlMy1hZTJlLTMwZTcwOTYyNjExNyIsImNsaWVudF9pZCI6ImZvbyJ9.vOx3WIajVeaPelFuYttvSjvOSXw5POwzQiZPxQmH6eSQTVR_YCHHzd0vh2a00g3spZ0-S7fZfkiFuNF-QJogGS-GER-B8p4c6mMrazN0x-wytMVM6xZrQbner0Uqu_uuK1vQs-gm2-2BFpydQtq-ZYicss21RSJTLK7fuH5DzHQ"
}
```


## Add the DDOS Features 

```  
servlet:
    maxQueryDepth: 100
    maxQueryComplexity: 100
    async-timeout: 5000

```

Make some test, to test the features. 

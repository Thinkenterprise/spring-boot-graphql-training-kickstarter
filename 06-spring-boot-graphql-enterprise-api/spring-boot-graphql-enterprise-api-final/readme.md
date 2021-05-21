# GraphQL Enterprise API Exercise 


## Add Validation 

The validation is already defined in the schema 


```  
directive @Size(min : Int = 0, max : Int = 2147483647, message : String = "graphql.validation.Size.message")
on ARGUMENT_DEFINITION | INPUT_FIELD_DEFINITION

```

And at least the validation itself.


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

Test the validation!! 
                     

## Exceptions  

We choose the Spring based ``@ExceptionHandler`` implementation to handle the mapping between **Java Exceptions** and **GraphQL Erros**.  



To enable the feature we have to add some spring configuration in our property file.  

```  
graphql:
  servlet:
    exception-handlers-enabled: true 

```

Throw a business exception ``RouteNotFoundException`` if the route with the fligtNumber not exists. 

```  
public Route route(String flightNumber) {

	Optional<Route> route = routeRepository.findByFlightNumber(flightNumber);

	if (route.isEmpty())
		throw new RouteNotFoundException("Route with flightnumber " + flightNumber + "doesnt exists");
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

## Context

### Custom Context 

The Context Classes `CustomGraphQLServletContextBuilder` and `CustomGraphQLServletContext`` are already exists. 


### Configuration 

Create a instance over the Spring Boot Configuration. 

```
@Configuration
public class GraphQLConfiguration {

	@Bean
	public CustomGraphQLServletContextBuilder customGraphQLServletContextBuilder() {
		return new CustomGraphQLServletContextBuilder();
	}
	
}

```

### Using the Context 

```
public List<Route> routes(int page, int size, DataFetchingEnvironment dataFetchingEnvironment)  {
			
		CustomGraphQLServletContext customGraphQLServletContext = (CustomGraphQLServletContext) dataFetchingEnvironment.getContext();
		log.info("Custom Context: " + customGraphQLServletContext.getUserId());
	
		Pageable pageable = PageRequest.of(page, size);

		Page<Route> pageResult = routeRepository.findAll(pageable);
		return pageResult.toList();
		
	}

```

## Security 


### OAuth 2 / JWT 

Check Spring Boot Security Dependency.  

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

Test the GraphQL API over Playground with a security header ( see header.md).   

```  
{
	"Authorization": "Bearer eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJzY29wZSI6WyJyZWFkIl0sImV4cCI6MjE0NDA4NjQ0MCwiYXV0aG9yaXRpZXMiOlsiUk9MRV9VU0VSIl0sInVzZXJfbmFtZSI6InRvbSIsImp0aSI6ImM4N2Q5NTNjLTZlZDAtNGRlMy1hZTJlLTMwZTcwOTYyNjExNyIsImNsaWVudF9pZCI6ImZvbyJ9.vOx3WIajVeaPelFuYttvSjvOSXw5POwzQiZPxQmH6eSQTVR_YCHHzd0vh2a00g3spZ0-S7fZfkiFuNF-QJogGS-GER-B8p4c6mMrazN0x-wytMVM6xZrQbner0Uqu_uuK1vQs-gm2-2BFpydQtq-ZYicss21RSJTLK7fuH5DzHQ"
}	
```

To authorize you can add a @PreAuthorized method on each method for example ``@PreAuthorize("hasRole('read')")`` 
with using the Security SPEL.  

### Add the DDOS Features 

```  
servlet:
    maxQueryDepth: 100
    maxQueryComplexity: 100
    async-timeout: 5000

```

Make some test, to test the features. 

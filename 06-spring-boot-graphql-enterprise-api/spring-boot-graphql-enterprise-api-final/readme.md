# GraphQL API Exercise 


## Add Validation 

### Based on Extended Validation 
First we have to add the **Extended Validation**. 

```  
<dependency>
	<groupId>com.graphql-java</groupId>
	<artifactId>graphql-java-extended-validation</artifactId>
	<version>16.0.0</version>
</dependency>

```

Next add the validation **Directive** Definition to the Schema.

```  
directive @Size(min : Int = 0, max : Int = 2147483647, message : String = "graphql.validation.Size.message")
on ARGUMENT_DEFINITION | INPUT_FIELD_DEFINITION

```

And at least the validation itself.


```  
route(flightNumber: String! @Size( min : 6, max : 6 )): Route

```
                     
### Based on Bean Validation JSR 303

First we have to add the **Bean Validation Dependency**. We use the Hibernate implementation of the BV Specification. 

```  
	<dependency>
		<groupId>org.hibernate.validator</groupId>
		<artifactId>hibernate-validator</artifactId>
	</dependency>

```

Add the Validation Annotation on the Java Input Object which is used. 

```  
public class RouteInput {
    
    @NotBlank
    String departure;
    String destination;
}
```

Activate the validation on the resolvers over ``@Validation`` and the validation on the input object over ``@valid``.

```  
@Component
@Validated
public class RootMutationResolver implements GraphQLMutationResolver {
	
   public Route updateRouteWithRouteInput(Long id, @Valid RouteInput routeInput) {
        Route route = routeRepository.findById(id).get();
        route.setDeparture(routeInput.getDeparture());
        route.setDestination(routeInput.getDestination());
        routeRepository.save(route);      
        return route;
    }
    
}
```




<!-- Schema Validation (Enterprise API Exercise) -->
		<dependency>
			<groupId>com.graphql-java</groupId>
			<artifactId>graphql-java-extended-validation</artifactId>
			<version>16.0.0</version>
		</dependency>



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
		throw new RouteNotFoundException("Route with flightnumer " + flightNumber + "doent exists");
	else
		return route.get();

}
```


Implements a ``@ExceptionHandler`` method in our ``RootQueryResolver`` resolver which map the business exception to a GraphQL Error. 


```  
@ExceptionHandler(value = RouteNotFoundException.class)
public GraphQLError exceptionHandler(RouteNotFoundException routeNotFoundException, ErrorContext error) {
		
 return GraphqlErrorBuilder.newError().message(routeNotFoundException.getMessage()).path(error.getPath()).build();
		
}

```
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

## Security 

### Basic Authentication 
 
Check Spring Boot Security Dependency.  

```  
	<dependency>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starter-security</artifactId>
	</dependency>
```

Check the Basic Authentication ``GraphQLBasicWebSecurityConfiguration`` configuration. 

To Enable the Security Configuration set the profile ``security`` and ``basic``. 

```  
spring: 
   profiles:
    active:
    - security
    - basic   	
```

Test the GraphQL API over Playground with a security header ( see header.md).  

```  
{
	"Authorization": "Basic dXNlcjpwYXNzd29yZA=="
}  	
```

To authorize you can add a @PreAuthorized method on each method for example ``@PreAuthorize("hasRole('read')")`` 
with using the Security SPEL.  



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




# GraphQL Application

## Add Spring Boot Web Starter 

Add Spring Boot Web Support based to provide **Servlet Features** technology.

``` 
<dependency>
	<groupId>org.springframework.boot</groupId>
     <artifactId>spring-boot-starter-web</artifactId>
</dependency>

```

## Add GraphQL Spring Boot Web Starter 

Add GraphQL **HTTP** Endpoint based on **Java EE Servlet** technology. 


``` 
<dependency>
	<groupId>com.graphql-java-kickstart</groupId>
	<artifactId>graphql-spring-boot-starter</artifactId>
	<version>11.1.0</version>
</dependency>
```

## Add GraphQL Srping Boot Tools Starter 

Add GrapQHL **Schema**-Support. 

``` 
<dependency>
	<groupId>com.graphql-java-kickstart</groupId>
	<artifactId>playground-spring-boot-starter</artifactId>
	<version>11.1.0</version>
</dependency>
```
 

## Add GraphQL Playground Starter 


Add **Plaground IDE** to work with GraphQL Queries on the GraphQL Backend. 

``` maven
<dependency>
	<groupId>com.graphql-java-kickstart</groupId>
	<artifactId>playground-spring-boot-starter</artifactId>
	<version>11.1.0</version>
</dependency>
```

## Add GraphQL Spring Boot Configuration 

``` 
graphql:
  tools:
    schemaLocationPattern: "**/*.graphql"
    introspection-enabled: true
  servlet:
    enabled: true
    mapping: /graphql
  playground:
    enabled: true
```


## Start Application 

``mvn spring-boot:run``

## Open Playground IDE

Start Playground an test the Application with the **Hello World** query. 

``localhost:4000/playground``


```
query {helloWorld}
```

It is also possible to execute the query via CURL. 

```
curl -X POST 'http://localhost:4000/graphql' -H 'Content-Type: application/json' -d '{"query":"{routes{id}}"}'
```



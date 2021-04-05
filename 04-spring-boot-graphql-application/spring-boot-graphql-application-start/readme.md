# GraphQL Application

## Add GraphQL Web Starter 

Add GraphQL **HTTP** Endpoint based on **Java EE Servlet** Technology. 


``` 
<dependency>
	<groupId>com.graphql-java-kickstart</groupId>
	<artifactId>graphql-spring-boot-starter</artifactId>
	<version>11.0.0</version>
</dependency>
```

## Add GraphQL Tools Starter 

Add GrapQHL **Schema**-Support. 

``` 
<dependency>
	<groupId>com.graphql-java-kickstart</groupId>
	<artifactId>playground-spring-boot-starter</artifactId>
	<version>11.0.0</version>
</dependency>
``` 

## Add GraphQL Playground Starter 

Add **Plaground IDE** to work with GraphQL Queries on the GraphQL Backend. 

``` maven
<dependency>
	<groupId>com.graphql-java-kickstart</groupId>
	<artifactId>playground-spring-boot-starter</artifactId>
	<version>11.0.0</version>
</dependency>
```

## Add GraphQL Tools Configuration

``` maven
graphql:
  tools:
    schemaLocationPattern: "**/*.graphql"
```

## Add GraphQL Servlet Configuration

``` maven
graphql:
  servlet:
    mapping: /graphql
    enabled: true
```

## Start Application 

``mvn spring-boot:run``

## Open Plaground IDE

Only to check that the IDE is working. It is'nt possible to execute a query because we have no GraphQL API Model configured!! 

``localhost:4000/playground``

## Open H2 Web Console 

Only to check or read the database. 

``localhost:4000/hs-console``
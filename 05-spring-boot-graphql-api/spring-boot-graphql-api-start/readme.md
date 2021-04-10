# GraphQL Schema Language



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


```
<dependency>
  <groupId>com.zhokhov.graphql</groupId>
  <artifactId>graphql-datetime-spring-boot-starter</artifactId>
  <version>4.0.0</version>
</dependency>
```
package com.thinkenterprise;

import org.springframework.stereotype.Component;

import graphql.kickstart.tools.GraphQLQueryResolver;

@Component
public class HelloWorldResolver implements GraphQLQueryResolver {

	public String helloWorld() {
		return "Hello World";
		
	}
	
}

package com.thinkenterprise.domain.route.graphql.exception;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.thinkenterprise.domain.route.exception.RouteException;
import com.thinkenterprise.domain.route.graphql.error.CustomGraphQLError;

import graphql.GraphQLError;

/**  
* GraphQL Spring Boot Training 
* Design and Development by Michael Schäfer 
* Copyright (c) 2020 
* All Rights Reserved.
* 
* @author Michael Schäfer
*/

@Component
public class GlobalGraphQLExceptionHandler {
	
	@ExceptionHandler(RouteException.class)
	public GraphQLError exception(RouteException routeException) {
		return new CustomGraphQLError("GlobalGraphQLExceptionHandler: Global GraphQL Exception Handler");
	}

}

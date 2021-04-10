package com.thinkenterprise.domain.route.graphql.resolver.query;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.thinkenterprise.domain.route.exception.RouteException;
import com.thinkenterprise.domain.route.graphql.context.CustomGraphQLServletContext;
import com.thinkenterprise.domain.route.graphql.error.CustomGraphQLError;
import com.thinkenterprise.domain.route.jpa.model.Route;
import com.thinkenterprise.domain.route.jpa.model.repository.RouteRepository;

import graphql.GraphQLError;
import graphql.kickstart.tools.GraphQLQueryResolver;
import graphql.schema.DataFetchingEnvironment;

/**  
* GraphQL Spring Boot Training 
* Design and Development by Michael Schäfer 
* Copyright (c) 2020 
* All Rights Reserved.
* 
* @author Michael Schäfer
*/

@Component
public class RootQueryResolver implements GraphQLQueryResolver {
	
	protected static Logger log = LoggerFactory.getLogger(RootQueryResolver.class);
	
	private RouteRepository routeRepository; 
	
	@Value("${route.exception}")
	private Boolean exception;
	
	@Autowired
	public RootQueryResolver(RouteRepository routeRepository) {
		this.routeRepository=routeRepository;	
	}
	
	// @PreAuthorize("hasAuthority('SCOPE_read')") // Profile: security & token
	//@PreAuthorize("hasRole('read')")  // Profile: security & basic
	public List<Route> routes(DataFetchingEnvironment dataFetchingEnvironment) {
		
		CustomGraphQLServletContext customGraphQLServletContext = (CustomGraphQLServletContext) dataFetchingEnvironment.getContext();
		
		log.debug(customGraphQLServletContext.getUserId());
		
		if(!exception)
			return routeRepository.findAll();
		else 
			 //throw GraphqlErrorException.newErrorException().message("GraphqlErrorException: Route Data Fetching doesent work").build();
			throw new RouteException("RouteException: Route Data Fetching doesent work");
		
	} 
	
	public Route route(String flightNumber) {
		return routeRepository.findByFlightNumber(flightNumber);
	}
	
	@ExceptionHandler(RouteException.class)
	public GraphQLError exception(RouteException routeException) {
		return new CustomGraphQLError("CustomGraphQLError: Exception Handler");
	}
	
}

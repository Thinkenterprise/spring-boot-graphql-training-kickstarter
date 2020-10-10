package com.thinkenterprise.domain.route.graphql.resolver.query;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.thinkenterprise.domain.route.graphql.context.CustomGraphQLServletContext;
import com.thinkenterprise.domain.route.graphql.context.CustomGraphQLServletContextBuilder;
import com.thinkenterprise.domain.route.graphql.error.RouteGraphQLError;
import com.thinkenterprise.domain.route.jpa.model.Route;
import com.thinkenterprise.domain.route.jpa.model.RouteException;
import com.thinkenterprise.domain.route.jpa.model.repository.RouteRepository;

import graphql.GraphQLError;
import graphql.kickstart.tools.GraphQLQueryResolver;
import graphql.schema.DataFetchingEnvironment;

/**  
* GraphQL Spring Boot Samples 
* Design and Development by Michael Schäfer 
* Copyright (c) 2019 
* All Rights Reserved.
* 
* @author Michael Schäfer
*/


@Component(RootQueryResolver.ROOT_QUERY_RESOLVER)
public class RootQueryResolver implements GraphQLQueryResolver {
	
	static final String ROOT_QUERY_RESOLVER = "com.thinkenterprise.domain.route.graphql.resolver.query.RootQueryResolver";
	protected static Logger log = LoggerFactory.getLogger(RootQueryResolver.class);
	
	private RouteRepository routeRepository; 
	
	@Value("${route.exception}")
	private Boolean exception;
	
	@Autowired
	public RootQueryResolver(RouteRepository routeRepository) {
		this.routeRepository=routeRepository;	
	}
	
	//@PreAuthorize("hasAuthority('SCOPE_read')")
	public List<Route> routes(DataFetchingEnvironment dataFetchingEnvironment) {
		
		CustomGraphQLServletContext customGraphQLServletContext = (CustomGraphQLServletContext) dataFetchingEnvironment.getContext();
		
		log.debug(customGraphQLServletContext.getUserId());
		
		if(!exception)
			return routeRepository.findAll();
		else 
			throw new RouteException("Test Exception ....");
	} 
	
	public Route route(String flightNumber) {
		return routeRepository.findByFlightNumber(flightNumber);
	}
	
	@ExceptionHandler(RouteException.class)
	public GraphQLError exception(RouteException routeException) {
		return new RouteGraphQLError(routeException.getMessage());
	}

}

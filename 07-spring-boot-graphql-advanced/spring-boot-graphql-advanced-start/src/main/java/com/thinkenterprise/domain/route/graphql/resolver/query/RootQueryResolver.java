package com.thinkenterprise.domain.route.graphql.resolver.query;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.thinkenterprise.domain.route.exceptions.RouteNotFoundException;
import com.thinkenterprise.domain.route.model.jpa.Route;
import com.thinkenterprise.domain.route.model.jpa.RouteRepository;
import com.thinkenterprise.graphql.context.CustomGraphQLServletContext;

import graphql.GraphQLError;
import graphql.GraphqlErrorBuilder;
import graphql.kickstart.spring.error.ErrorContext;
import graphql.kickstart.spring.error.ThrowableGraphQLError;
import graphql.kickstart.tools.GraphQLQueryResolver;
import graphql.schema.DataFetchingEnvironment;

/**
 * GraphQL Spring Boot Training Design and Development by Michael Schäfer
 * Copyright (c) 2020 All Rights Reserved.
 * 
 * @author Michael Schäfer
 */

@Component
public class RootQueryResolver implements GraphQLQueryResolver {

	protected static Logger log = LoggerFactory.getLogger(RootQueryResolver.class);

	private RouteRepository routeRepository;

	@Autowired
	public RootQueryResolver(RouteRepository routeRepository) {
		this.routeRepository = routeRepository;
	}

	
	@PreAuthorize("hasAuthority('SCOPE_read')")
	public CompletableFuture<Optional<Route>> route(String flightNumber) {
		return CompletableFuture.supplyAsync(() ->  routeRepository.findByFlightNumber(flightNumber)); 
		                                           
	}
	
	public CompletableFuture<List<Route>> routes(int page, int size, DataFetchingEnvironment dataFetchingEnvironment) throws InterruptedException  {
		
		CustomGraphQLServletContext customGraphQLServletContext = (CustomGraphQLServletContext) dataFetchingEnvironment.getContext();
		log.info("Custom Context: " + customGraphQLServletContext.getUserId());
			
		Pageable pageable = PageRequest.of(page, size);

		Page<Route> pageResult = routeRepository.findAll(pageable);
		
		return CompletableFuture.supplyAsync(() ->  pageResult.toList()); 	
	}
	
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
	
}

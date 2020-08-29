package com.thinkenterprise.domain.route.resolver.query;

import java.util.List;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.thinkenterprise.domain.route.Route;
import com.thinkenterprise.domain.route.RouteRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**  
* GraphQL Spring Boot Samples 
* Design and Development by Michael Schäfer 
* Copyright (c) 2019 
* All Rights Reserved.
* 
* @author Michael Schäfer
*/


@Component
public class RootQueryResolver implements GraphQLQueryResolver {

	private RouteRepository routeRepository;
	
	@Autowired
	public RootQueryResolver(RouteRepository routeRepository) {
		this.routeRepository=routeRepository;	
	}
	
	public List<Route> routes() {
		return routeRepository.findAll();
	} 
	
	public Route route(String flightNumber) {
		return routeRepository.findByFlightNumber(flightNumber);
	}

}

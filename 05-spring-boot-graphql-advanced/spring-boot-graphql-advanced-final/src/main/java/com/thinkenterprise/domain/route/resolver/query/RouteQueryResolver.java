package com.thinkenterprise.domain.route.resolver.query;

import java.util.List;

import com.coxautodev.graphql.tools.GraphQLResolver;
import com.thinkenterprise.domain.route.Flight;
import com.thinkenterprise.domain.route.FlightRepository;
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
public class RouteQueryResolver implements GraphQLResolver<Route> {

    private FlightRepository flightRepository;
    private RouteRepository routeRepository;

    @Autowired
    public RouteQueryResolver(RouteRepository routeRepository,
    					      FlightRepository flightRepository) {
    	this.routeRepository=routeRepository;
        this.flightRepository=flightRepository;
    }

    public List<Flight> flights(Route route) {
        return flightRepository.findByRoute(route);	
    }

    public Route route(Route route) {
    	return routeRepository.findByRoute(route);
    }

}
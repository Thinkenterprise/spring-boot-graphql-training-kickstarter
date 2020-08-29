package com.thinkenterprise.domain.route.resolver.mutation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.thinkenterprise.domain.route.Route;
import com.thinkenterprise.domain.route.RouteRepository;
import com.thinkenterprise.domain.route.publisher.RouteUpdatePublisher;

/**  
* GraphQL Spring Boot Samples 
* Design and Development by Michael Schäfer 
* Copyright (c) 2019 
* All Rights Reserved.
* 
* @author Michael Schäfer
*/

@Service 
public class RootMutationResolver implements GraphQLMutationResolver {

    private RouteRepository routeRepository;
    private RouteUpdatePublisher routeUpdatePublisher;

    @Autowired
    public RootMutationResolver(RouteRepository routeRepository,
                                RouteUpdatePublisher routeUpdatePublisher) {
        this.routeRepository=routeRepository;	
        this.routeUpdatePublisher=routeUpdatePublisher;
    }
 
    public Route createRoute(String flightNumber) {
        Route route = new Route(flightNumber);
        routeRepository.save(route);
        return route; 
    }

    public Route updateRoute(Long id, String departure) {
        Route route = routeRepository.findById(id).get();
        route.setDeparture(departure);
        routeRepository.save(route);
        return route;
    }

    public Route updateRouteWithRouteInput(Long id, RouteInput input) {
        Route route = routeRepository.findById(id).get();
        route.setDeparture(input.getDeparture());
        route.setDestination(input.getDestination());
        routeRepository.save(route);
        return route;
    }

    public Boolean isDeleteRoute(Long id) {
        routeRepository.deleteById(id);
        return true;

    }

}
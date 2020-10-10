package com.thinkenterprise.domain.route.graphql.resolver.mutation;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.thinkenterprise.domain.route.graphql.publisher.RouteSubscriptionNotifier;
import com.thinkenterprise.domain.route.jpa.model.Route;
import com.thinkenterprise.domain.route.jpa.model.repository.RouteRepository;

import graphql.kickstart.tools.GraphQLMutationResolver;

/**  
* GraphQL Spring Boot Samples 
* Design and Development by Michael Schäfer 
* Copyright (c) 2019 
* All Rights Reserved.
* 
* @author Michael Schäfer
*/

@Service(value = RootMutationResolver.ROOT_MUTATION_RESOLVER)
@Validated
public class RootMutationResolver implements GraphQLMutationResolver {
	
	static final String ROOT_MUTATION_RESOLVER = "com.thinkenterprise.domain.route.graphql.resolver.mutation.RootMutationResolver";

    private RouteRepository routeRepository;
    private RouteSubscriptionNotifier routeSubscriptionNotifier;

    @Autowired
    public RootMutationResolver(RouteRepository routeRepository,
    							RouteSubscriptionNotifier routeUpdatePublisher) {
        this.routeRepository=routeRepository;	
        this.routeSubscriptionNotifier=routeUpdatePublisher;
    }
 
    public Route createRoute(String flightNumber) {
        Route route = new Route(flightNumber);
        routeRepository.save(route);
        routeSubscriptionNotifier.emit(route);
        return route; 
    }

    public Route updateRoute(Long id, String departure) {
        Route route = routeRepository.findById(id).get();
        route.setDeparture(departure);
        routeRepository.save(route);
        routeSubscriptionNotifier.emit(route);
        return route;
    }

    public Route updateRouteWithRouteInput(Long id, @Valid RouteInput routeInput) {
        Route route = routeRepository.findById(id).get();
        route.setDeparture(routeInput.getDeparture());
        route.setDestination(routeInput.getDestination());
        routeRepository.save(route);
        routeSubscriptionNotifier.emit(route);
        return route;
    }

    public Boolean isDeleteRoute(Long id) {
        routeRepository.deleteById(id);
        return true;

    }

}
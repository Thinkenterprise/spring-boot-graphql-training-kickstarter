package com.thinkenterprise.domain.route.graphql.resolver.mutation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.thinkenterprise.domain.route.graphql.publisher.RouteSubscriptionNotifier;
import com.thinkenterprise.domain.route.model.jpa.Route;
import com.thinkenterprise.domain.route.model.jpa.RouteRepository;

import graphql.kickstart.tools.GraphQLMutationResolver;

/**  
* GraphQL Spring Boot Training 
* Design and Development by Michael Schäfer 
* Copyright (c) 2020 
* All Rights Reserved.
* 
* @author Michael Schäfer
*/

@Component
@Transactional
public class RootMutationResolver implements GraphQLMutationResolver {
	

    private RouteRepository routeRepository;
    private RouteSubscriptionNotifier projectReactorRouteSubscriptionNotifier;


    @Autowired
    public RootMutationResolver(RouteRepository routeRepository, 
    							RouteSubscriptionNotifier projectReactorRouteSubscriptionNotifier) {
    	this.projectReactorRouteSubscriptionNotifier=projectReactorRouteSubscriptionNotifier;
        this.routeRepository=routeRepository;	 
    }
 
    public Route createRoute(String flightNumber) {
    	Route route = new Route(flightNumber);
        routeRepository.save(route);
        projectReactorRouteSubscriptionNotifier.emit(route);
        return route; 
    }
}
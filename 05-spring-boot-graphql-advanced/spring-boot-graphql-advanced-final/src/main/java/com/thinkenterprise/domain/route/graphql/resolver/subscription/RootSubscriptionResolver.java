package com.thinkenterprise.domain.route.graphql.resolver.subscription;

import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.thinkenterprise.domain.route.graphql.publisher.RouteSubscriptionNotifier;
import com.thinkenterprise.domain.route.graphql.publisher.RxJavaRouteSubscriptionNotifier;
import com.thinkenterprise.domain.route.jpa.model.Route;

import graphql.kickstart.tools.GraphQLSubscriptionResolver;

/**
 * GraphQL Spring Boot Samples Design and Development by Michael Schäfer
 * Copyright (c) 2019 All Rights Reserved.
 * 
 * @author Michael Schäfer
 */

@Component(value = RootSubscriptionResolver.ROOT_SUBSCRIPTION_RESOLVER)
public class RootSubscriptionResolver implements GraphQLSubscriptionResolver {

	static final String ROOT_SUBSCRIPTION_RESOLVER = "com.thinkenterprise.domain.route.graphql.resolver.mutation.RootSubscriptionResolver";

	private RouteSubscriptionNotifier routeSubscriptionNotifier;

	@Autowired
	public RootSubscriptionResolver(RouteSubscriptionNotifier routeUpdatePublisher) {
		this.routeSubscriptionNotifier = routeUpdatePublisher;
	}

	public Publisher<Route> registerRouteCreated() {
		return routeSubscriptionNotifier.getPublisher();
	}

}
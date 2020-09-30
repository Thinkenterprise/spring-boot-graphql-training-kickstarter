package com.thinkenterprise.domain.route.graphql.publisher;

import org.reactivestreams.Publisher;

import com.thinkenterprise.domain.route.jpa.model.Route;

public interface RouteSubscriptionNotifier {
	
	   public void emit(Route route);

	    public Publisher <Route> getPublisher();

}

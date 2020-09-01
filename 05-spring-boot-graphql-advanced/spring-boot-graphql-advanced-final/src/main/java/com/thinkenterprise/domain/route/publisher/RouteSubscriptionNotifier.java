package com.thinkenterprise.domain.route.publisher;

import org.reactivestreams.Publisher;

import com.thinkenterprise.domain.route.Route;

public interface RouteSubscriptionNotifier {
	
	   public void emit(Route route);

	    public Publisher <Route> getPublisher();

}

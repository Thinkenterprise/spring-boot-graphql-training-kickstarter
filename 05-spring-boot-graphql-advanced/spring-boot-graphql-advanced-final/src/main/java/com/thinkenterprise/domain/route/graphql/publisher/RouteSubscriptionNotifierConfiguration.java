package com.thinkenterprise.domain.route.graphql.publisher;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class RouteSubscriptionNotifierConfiguration {
	
	@Bean
	@Profile("rxJava")
	public RouteSubscriptionNotifier rxJavaRouteSubscriptionNotifier() {
		return new RxJavaRouteSubscriptionNotifier();
		
	}
	
	@Bean
	@Profile("projectReactor")
	public RouteSubscriptionNotifier projectReactorRouteSubscriptionNotifier() {
		return new ProjectReactorRouteSubscriptionNotifier();
		
	}
	
}

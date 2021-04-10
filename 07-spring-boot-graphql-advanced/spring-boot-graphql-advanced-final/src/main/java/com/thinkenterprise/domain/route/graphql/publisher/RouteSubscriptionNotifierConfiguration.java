package com.thinkenterprise.domain.route.graphql.publisher;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**  
* GraphQL Spring Boot Training 
* Design and Development by Michael Schäfer 
* Copyright (c) 2020 
* All Rights Reserved.
* 
* @author Michael Schäfer
*/

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

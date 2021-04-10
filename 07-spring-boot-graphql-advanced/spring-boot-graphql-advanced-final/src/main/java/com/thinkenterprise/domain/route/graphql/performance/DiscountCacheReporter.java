package com.thinkenterprise.domain.route.graphql.performance;

import javax.annotation.PreDestroy;

import org.dataloader.DataLoader;
import org.dataloader.stats.Statistics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.thinkenterprise.domain.route.graphql.context.CustomGraphQLServletContextBuilder;

import graphql.kickstart.servlet.context.GraphQLServletContextBuilder;

/**  
* GraphQL Spring Boot Training 
* Design and Development by Michael Schäfer 
* Copyright (c) 2020 
* All Rights Reserved.
* 
* @author Michael Schäfer
*/

@Component
@Profile("performance")
public class DiscountCacheReporter {

	@Autowired private GraphQLServletContextBuilder customGraphQLServletContextBuilder;
	
	@PreDestroy
	public void destroy() {
		  
		  DataLoader<Long , Float> discountDataLoader = ((CustomGraphQLServletContextBuilder) customGraphQLServletContextBuilder).
				  																getDefaultGraphQLServletContext().
				  																getDataLoaderRegistry().
				  																get().
				  																getDataLoader("discount");
		  Statistics statistics = discountDataLoader.getStatistics();
	      
	      System.out.println("load : " + statistics.getLoadCount());
	      System.out.println("batch load: " + statistics.getBatchLoadCount());
	      System.out.println("cache hit: " + statistics.getCacheHitCount());
	      System.out.println("cache hit ratio: " + statistics.getCacheHitRatio());
	  }
	
	
}

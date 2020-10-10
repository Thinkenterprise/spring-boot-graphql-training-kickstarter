package com.thinkenterprise.configuration.graphql;


import javax.annotation.PreDestroy;

import org.dataloader.DataLoader;
import org.dataloader.stats.Statistics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.thinkenterprise.domain.employee.jpa.model.Pilot;
import com.thinkenterprise.domain.route.graphql.context.CustomGraphQLServletContextBuilder;

import graphql.kickstart.servlet.context.GraphQLServletContextBuilder;
import graphql.kickstart.tools.SchemaParserDictionary;

/**  
* GraphQL Spring Boot Samples 
* Design and Development by Michael Schäfer 
* Copyright (c) 2019 
* All Rights Reserved.
* 
* @author Michael Schäfer
*/


@Configuration
public class GraphQLConfiguration {

  
 	
  @Bean
  public SchemaParserDictionary schemaParserDictionary() {
    return new SchemaParserDictionary()
        .add(Pilot.class);
  }
  
  @Bean
  public GraphQLServletContextBuilder graphQLServletContextBuilder() {
	return new CustomGraphQLServletContextBuilder();  
  }
  
  
  
  

}
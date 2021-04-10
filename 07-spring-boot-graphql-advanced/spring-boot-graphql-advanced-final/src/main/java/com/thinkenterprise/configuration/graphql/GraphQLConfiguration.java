package com.thinkenterprise.configuration.graphql;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.thinkenterprise.domain.employee.jpa.model.Pilot;
import com.thinkenterprise.domain.route.graphql.context.CustomGraphQLServletContextBuilder;

import graphql.kickstart.servlet.context.GraphQLServletContextBuilder;
import graphql.kickstart.tools.SchemaParserDictionary;

/**  
* GraphQL Spring Boot Training 
* Design and Development by Michael Schäfer 
* Copyright (c) 2020 
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
  @Profile("context")
  public GraphQLServletContextBuilder graphQLServletContextBuilder() {
	return new CustomGraphQLServletContextBuilder();  
  }
  
}
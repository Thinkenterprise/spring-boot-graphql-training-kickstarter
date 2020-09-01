package com.thinkenterprise;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.thinkenterprise.domain.employee.Pilot;

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

}
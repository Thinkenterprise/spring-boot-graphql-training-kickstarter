package com.thinkenterprise.configuration.graphql;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.thinkenterprise.domain.employee.model.jpa.Attendant;
import com.thinkenterprise.domain.employee.model.jpa.Pilot;
import com.thinkenterprise.domain.route.graphql.publisher.ProjectReactorRouteSubscriptionNotifier;
import com.thinkenterprise.domain.route.graphql.publisher.RouteSubscriptionNotifier;
import com.thinkenterprise.graphql.context.CustomGraphQLServletContextBuilder;
import com.thinkenterprise.graphql.directive.UppercaseDirective;

import graphql.kickstart.servlet.context.GraphQLServletContextBuilder;
import graphql.kickstart.tools.SchemaParserDictionary;
import graphql.kickstart.tools.boot.SchemaDirective;
import graphql.validation.rules.OnValidationErrorStrategy;
import graphql.validation.rules.ValidationRules;
import graphql.validation.schemawiring.ValidationSchemaWiring;

/**
 * GraphQL Spring Boot Training Design and Development by Michael Schäfer
 * Copyright (c) 2020 All Rights Reserved.
 * 
 * @author Michael Schäfer
 */

@Configuration
public class GraphQLConfiguration {

	@Bean
	public SchemaParserDictionary schemaParserDictionary() {
		return new SchemaParserDictionary().add(Pilot.class).add(Attendant.class);
	}

	@Bean
	public RouteSubscriptionNotifier projectReactorRouteSubscriptionNotifier() {
		return new ProjectReactorRouteSubscriptionNotifier();
	}

	@Bean
	public SchemaDirective myCustomDirective() {
		return new SchemaDirective("uppercase", new UppercaseDirective());
	}

	@Bean
	public CustomGraphQLServletContextBuilder customGraphQLServletContextBuilder() {
		return new CustomGraphQLServletContextBuilder();
	}
	
	
	@Bean
	public ValidationSchemaWiring validationRules() {

		ValidationRules validationRules = ValidationRules.newValidationRules()
														 .onValidationErrorStrategy(OnValidationErrorStrategy.RETURN_NULL).build();
		return new ValidationSchemaWiring(validationRules);
	}

}
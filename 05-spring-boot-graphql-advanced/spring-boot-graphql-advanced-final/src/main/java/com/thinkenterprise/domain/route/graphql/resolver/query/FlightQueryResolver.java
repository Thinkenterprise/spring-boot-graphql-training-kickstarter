package com.thinkenterprise.domain.route.graphql.resolver.query;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.dataloader.DataLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.thinkenterprise.domain.employee.jpa.model.Employee;
import com.thinkenterprise.domain.employee.jpa.model.EmployeeRepository;
import com.thinkenterprise.domain.route.jpa.model.Flight;
import com.thinkenterprise.domain.route.jpa.model.Route;
import com.thinkenterprise.domain.route.jpa.model.repository.RouteRepository;
import com.thinkenterprise.domain.route.service.DiscountService;

import graphql.kickstart.tools.GraphQLResolver;
import graphql.schema.DataFetchingEnvironment;


/**  
* GraphQL Spring Boot Training 
* Design and Development by Michael Schäfer 
* Copyright (c) 2020 
* All Rights Reserved.
* 
* @author Michael Schäfer
*/


@Component(FlightQueryResolver.FLIGHT_QUERY_RESOLVER)
public class FlightQueryResolver implements GraphQLResolver<Flight> {

	static final String FLIGHT_QUERY_RESOLVER = "com.thinkenterprise.domain.route.graphql.resolver.query.FlightQueryResolver";
	
    private EmployeeRepository employeeRepository;
    private RouteRepository routeRepository;
    private DiscountService discountService;

    @Autowired
    public FlightQueryResolver(RouteRepository routeRepository,
    						   EmployeeRepository employeeRepository,
    						   DiscountService discountService) {
        this.employeeRepository=employeeRepository;
        this.routeRepository=routeRepository;
        this.discountService=discountService;
    }

    public List<Employee> employees(Flight flight) {
        return employeeRepository.findByFlight(flight);
    }
    
    public Route route(Flight flight) {
    	return routeRepository.findById(flight.getRoute().getId()).get();
    }
    
    /* Profile performance
    public CompletableFuture<Float> discount(Flight flight, DataFetchingEnvironment dataFetchingEnvironment) { 
  
    	DataLoader<Long,Float> discoutDataLoader = dataFetchingEnvironment.getDataLoader("discount");
    	return discoutDataLoader.load(flight.getId());
    	
    } 
    */ 
    
    /* Profile performance
    public Float discount(Flight flight) {  	
    	return discountService.getDiscount(flight.getId());
    } 
   */
}
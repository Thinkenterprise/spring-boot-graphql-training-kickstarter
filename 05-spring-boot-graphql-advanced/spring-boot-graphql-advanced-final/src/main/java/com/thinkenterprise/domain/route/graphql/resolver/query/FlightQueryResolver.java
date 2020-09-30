package com.thinkenterprise.domain.route.graphql.resolver.query;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.thinkenterprise.domain.employee.jpa.model.Employee;
import com.thinkenterprise.domain.employee.jpa.model.EmployeeRepository;
import com.thinkenterprise.domain.route.jpa.model.Flight;
import com.thinkenterprise.domain.route.jpa.model.Route;
import com.thinkenterprise.domain.route.jpa.model.RouteRepository;

import graphql.kickstart.tools.GraphQLResolver;


/**  
* GraphQL Spring Boot Samples 
* Design and Development by Michael Schäfer 
* Copyright (c) 2019 
* All Rights Reserved.
* 
* @author Michael Schäfer
*/


@Component(FlightQueryResolver.FLIGHT_QUERY_RESOLVER)
public class FlightQueryResolver implements GraphQLResolver<Flight> {

	static final String FLIGHT_QUERY_RESOLVER = "com.thinkenterprise.domain.route.graphql.resolver.query.FlightQueryResolver";
	
    private EmployeeRepository employeeRepository;
    private RouteRepository routeRepository;

    @Autowired
    public FlightQueryResolver(RouteRepository routeRepository,
    						   EmployeeRepository employeeRepository) {
        this.employeeRepository=employeeRepository;
        this.routeRepository=routeRepository;
    }

    public List<Employee> employees(Flight flight) {
        return employeeRepository.findByFlight(flight);
    }
    
    public Route route(Flight flight) {
    	return routeRepository.findById(flight.getRoute().getId()).get();
    }

}
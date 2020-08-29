package com.thinkenterprise.domain.route.resolver.query;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.coxautodev.graphql.tools.GraphQLResolver;
import com.thinkenterprise.domain.employee.Employee;
import com.thinkenterprise.domain.employee.EmployeeRepository;
import com.thinkenterprise.domain.route.Flight;
import com.thinkenterprise.domain.route.Route;
import com.thinkenterprise.domain.route.RouteRepository;


/**  
* GraphQL Spring Boot Samples 
* Design and Development by Michael Schäfer 
* Copyright (c) 2019 
* All Rights Reserved.
* 
* @author Michael Schäfer
*/

@Component
public class FlightQueryResolver implements GraphQLResolver<Flight> {

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
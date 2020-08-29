package com.thinkenterprise.domain.employee;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.thinkenterprise.domain.route.Flight;
import com.thinkenterprise.domain.route.Route;

/**  
* GraphQL Spring Boot Samples 
* Design and Development by Michael Schäfer 
* Copyright (c) 2019 
* All Rights Reserved.
* 
* @author Michael Schäfer
*/

public interface EmployeeRepository extends CrudRepository<Employee, Long>{

	@Query("select e from Employee e")
	List<Employee> findAll();
	
	@Query( "select e from Employee e where e.flight = :flight")
	List<Employee> findByFlight(@Param("flight") Flight flight);
}

package com.thinkenterprise.domain.route.jpa.model;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

/**  
* GraphQL Spring Boot Samples 
* Design and Development by Michael Schäfer 
* Copyright (c) 2019 
* All Rights Reserved.
* 
* @author Michael Schäfer
*/

public interface FlightRepository extends CrudRepository<Flight, Long> {
		
	@Query( "select f from Flight f where f.route = :route")
	List<Flight> findByRoute(@Param("route") Route route);

}


package com.thinkenterprise.domain.route;

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

public interface RouteRepository extends CrudRepository<Route, Long>{
	
	Route findByFlightNumber(String flightNumber);
	
	@Query("select r from Route r")
	List<Route> findAll();
	
	@Query( "select r from Route r where r.parent = :route")
	Route findByRoute(@Param("route") Route route);
	
	
}
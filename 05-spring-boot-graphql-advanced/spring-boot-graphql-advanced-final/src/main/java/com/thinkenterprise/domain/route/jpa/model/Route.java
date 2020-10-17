package com.thinkenterprise.domain.route.jpa.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.thinkenterprise.domain.jpa.AbstractEntity;

/**  
* GraphQL Spring Boot Training 
* Design and Development by Michael Schäfer 
* Copyright (c) 2020 
* All Rights Reserved.
* 
* @author Michael Schäfer
*/

@Entity
public class Route extends AbstractEntity {

	private String flightNumber;
	private String departure;
	private String destination;
	private Boolean disabled;

	@OneToOne
	//@JoinColumn(name="id", referencedColumnName="parent_id", nullable=true)
	private Route parent;

	@OneToMany(mappedBy = "route", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
	private Set<Flight> flights;

	public Route() {
		super();
	}

	public Route(String flightNumber) {
		super();
		this.flightNumber = flightNumber;
	}

	public Route(String flightNumber, String departure, String destination, Boolean disabled) {
		this.flightNumber = flightNumber;
		this.destination = destination;
		this.departure = departure;
		this.disabled = disabled;
	}

	public void setDisabled(Boolean disabled) {
		this.disabled = disabled;
	}

	public Boolean getDisabled() {
		return this.disabled;
	}

	public String getDeparture() {
		return departure;
	}

	public void setDeparture(String departure) {
		this.departure = departure;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public String getFlightNumber() {
		return flightNumber;
	}

	public void setFlightNumber(String number) {
		this.flightNumber = number;
	}

	public Set<Flight> getFlights() {
		return flights;
	}

	public void setFlights(Set<Flight> flights) {
		this.flights = flights;
	}

	public void setParent(Route route) {
		this.parent = route;
	}

	public Route getParent() {
		return this.parent;
	}

}

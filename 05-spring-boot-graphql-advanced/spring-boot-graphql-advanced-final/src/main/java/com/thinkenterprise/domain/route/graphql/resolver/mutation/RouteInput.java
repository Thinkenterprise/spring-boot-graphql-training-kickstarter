package com.thinkenterprise.domain.route.graphql.resolver.mutation;

import javax.validation.constraints.NotBlank;

public class RouteInput {
    
	@NotBlank
    String departure;
    String destination;
    
    public String getDeparture() {
        return this.departure;
    }

    public void setDeparture(String depature) {
        this.departure=depature;
    }

    public String getDestination() {
        return this.destination;
    }

    public void setDestination(String destination) {
        this.destination=destination;
    }
}
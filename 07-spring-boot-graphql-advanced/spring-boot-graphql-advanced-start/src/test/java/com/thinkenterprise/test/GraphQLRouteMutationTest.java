package com.thinkenterprise.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;

import com.graphql.spring.boot.test.GraphQLResponse;
import com.graphql.spring.boot.test.GraphQLTestTemplate;

/**  
* GraphQL Spring Boot Training 
* Design and Development by Michael Schäfer 
* Copyright (c) 2020 
* All Rights Reserved.
* 
* @author Michael Schäfer
*/


@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode=ClassMode.BEFORE_EACH_TEST_METHOD)
public class GraphQLRouteMutationTest {
	
	@Autowired
    private GraphQLTestTemplate graphQLTestTemplate;
    
    @Test
    public void assertThatCreateRouteWorks() throws IOException {       
        GraphQLResponse response  = graphQLTestTemplate.postForResource("createRoute.graphql");
        assertNotNull(response);
        assertTrue(response.isOk());
        assertEquals("RO311", response.get("$.data.createRoute.flightNumber"));
        assertEquals("1", response.get("$.data.createRoute.id"));
    }
    
    @Test
    public void assertThatUpdateRouteWorks() throws IOException {       
        GraphQLResponse response  = graphQLTestTemplate.postForResource("updateRoute.graphql");
        assertNotNull(response);
        assertTrue(response.isOk());
        assertEquals("MUC", response.get("$.data.updateRoute.departure"));
    }
    
    @Test
    public void assertThatDeleteRouteWorks() throws IOException {       
        GraphQLResponse response  = graphQLTestTemplate.postForResource("deleteRoute.graphql");
        assertNotNull(response);
        assertTrue(response.isOk());
        assertEquals(true, response.get("$.data.deleteRoute", Boolean.class));
    }
      
}

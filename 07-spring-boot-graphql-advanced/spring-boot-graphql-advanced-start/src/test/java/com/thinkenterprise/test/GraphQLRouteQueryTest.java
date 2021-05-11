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
public class GraphQLRouteQueryTest {

    @Autowired
    private GraphQLTestTemplate graphQLTestTemplate;
    
    @Test
    public void assertThatRoutesWorks() throws IOException { 
        GraphQLResponse response  = graphQLTestTemplate.postForResource("routes.graphql");
        assertNotNull(response);
        assertTrue(response.isOk());
        assertEquals("101", response.get("$.data.routes[0].id"));
    }
    
    @Test
    public void assertThatRouteWorks() throws IOException { 
        GraphQLResponse response  = graphQLTestTemplate.postForResource("route.graphql");
        assertNotNull(response);
        assertTrue(response.isOk());
        assertEquals("101", response.get("$.data.route.id"));
    }
     
}
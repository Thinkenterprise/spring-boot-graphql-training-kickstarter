package com.thinkenterprise.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutionException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;

import com.graphql.spring.boot.test.GraphQLResponse;
import com.graphql.spring.boot.test.GraphQLTestSubscription;
import com.graphql.spring.boot.test.GraphQLTestTemplate;
import com.graphql.spring.boot.test.assertions.GraphQLFieldAssert;

/**  
* GraphQL Spring Boot Training 
* Design and Development by Michael Schäfer 
* Copyright (c) 2020 
* All Rights Reserved.
* 
* @author Michael Schäfer
*/

@SpringBootTest(webEnvironment=WebEnvironment.DEFINED_PORT)
public class GraphQLRouteSubcriptionTest {

    @Autowired
    private GraphQLTestSubscription graphQLTestSubscription;
    
    @Autowired
    private GraphQLTestTemplate graphQLTestTemplate;
    
    @Test
    public void testRegisterSubscriptionRouteCreated() throws InterruptedException, IOException, ExecutionException {
    	
    	CompletableFuture<Void> cf = CompletableFuture.runAsync(() -> graphQLTestSubscription.start("subcription-register.graphql")
    			                                                                           .awaitAndGetNextResponse(10000)
    			                                                                           .assertThatDataField());
    	
    	Thread.sleep(1000);
    		
    	GraphQLResponse response  = graphQLTestTemplate.postForResource("createRoute.graphql");
        assertNotNull(response);
        assertTrue(response.isOk());
        assertEquals("RO311", response.get("$.data.createRoute.flightNumber"));
        assertEquals("1", response.get("$.data.createRoute.id"));
        
        cf.get();
     	
    	
    }
     
}
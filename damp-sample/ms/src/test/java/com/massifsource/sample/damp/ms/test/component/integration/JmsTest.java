/**
 * MassifSource
 * @year 2015
 * @author nsarychev
 */
package com.massifsource.sample.damp.ms.test.component.integration;

import static org.testng.Assert.assertEquals;

import java.io.IOException;

import org.json.JSONObject;
import org.testng.annotations.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.massifsource.sample.damp.common.DataReplicationRequest;
import com.massifsource.sample.damp.common.Operation;
import com.massifsource.sample.damp.ms.test.component.context.ComponentContextBase;

@Test(groups = "basic-tests" )
public class JmsTest extends ComponentContextBase {
    
    @Test
    public void testJmsPut() throws JsonParseException, JsonMappingException, IOException, InterruptedException {
        
        JSONObject json;
        for (int i = 0; i < 5; i++) {
            json = new JSONObject();
            json.put("productId", (8679 + i));
            jmsTemplate.convertAndSend(new DataReplicationRequest("product_state", json.toString(),
                Operation.PUT));
        }
        waitOnQueToBeEmpty();
        assertEquals(destinationRestService.getCount("product_state"), 5);
    }
    
    @Test(dependsOnMethods = {"testJmsPut"})
    public void testJmsDelete() throws JsonParseException, JsonMappingException, IOException, InterruptedException {
        for (int i = 0; i < 5; i++) {
            int key = 8679 + i;
            jmsTemplate.convertAndSend(new DataReplicationRequest("product_state", String.valueOf(key),
                Operation.DELETE));
        }
        waitOnQueToBeEmpty();
        assertEquals(destinationRestService.getCount("product_state"), 0);
        
    }

}

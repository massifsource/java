/**
 * MassifSource
 * @year 2015
 * @author nsarychev
 */
package com.massifsource.sample.damp.ms.test.command.integration;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.massifsource.sample.damp.ms.test.command.context.CommandContextBase;
import com.massifsource.sample.damp.ms.test.common.TestDataProvider;

@Test(groups = "listener-command-tests")
public class ListenerIntegrationTest extends CommandContextBase {
        
    @BeforeClass
    public void assertEmpty() throws ClientProtocolException, IOException {
        assertEquals(sourceRestService.getCount("product_state"), 0);
    }
    
    @Test
    public void testUrlProviders() {
        assertEquals(destinationRestService.getRestUrlProvider().getRestUrlBase(), "http://localhost:9061");
        assertEquals(sourceRestService.getRestUrlProvider().getRestUrlBase(), "http://localhost:9060");
        assertEquals(destinationRestService.getRestUrlProvider().getRestUrlBaseAndMapName("product_state"), "http://localhost:9061/maps/product_state");
    }
    
    @Test
    public void testAddRemoveListener() throws JsonParseException, JsonMappingException, IOException, InterruptedException {
        String id = commandSupportService.addListener("product_state");
        assertNotNull(id);
        TestDataProvider.populateProductState(5, sourceRestService, 8679);
        waitOnQueToBeEmpty();
        assertEquals(sourceRestService.getCount("product_state"), 5);
        assertEquals(destinationRestService.getCount("product_state"), 5);
        
        sourceRestService.delete("product_state", 8679);
        assertEquals(sourceRestService.getCount("product_state"), 4);
        Thread.sleep(100);
        assertEquals(destinationRestService.getCount("product_state"), 4);
    }
    
    @Test
    public void testAddRemoveAllListener() throws JsonParseException, JsonMappingException, IOException, InterruptedException {
        commandSupportService.addListener("product_state"); 
        
        TestDataProvider.populateProductState(5, sourceRestService, 8679);
        TestDataProvider.populateProductState(5, sourceRestService, 8679);
       
        waitOnQueToBeEmpty();
        assertEquals(sourceRestService.getCount("product_state"), 5);
        assertEquals(destinationRestService.getCount("product_state"), 5);
        
        sourceRestService.delete("product_state", 8679);
        assertEquals(sourceRestService.getCount("product_state"), 4);
        waitOnQueToBeEmpty();
        assertEquals(destinationRestService.getCount("product_state"), 4);
                
        commandSupportService.removeAllListeners();
        TestDataProvider.populateProductState(1, sourceRestService, 9679);
        assertEquals(sourceRestService.getCount("product_state"), 5);
        assertEquals(destinationRestService.getCount("product_state"), 4);
    }
  
}

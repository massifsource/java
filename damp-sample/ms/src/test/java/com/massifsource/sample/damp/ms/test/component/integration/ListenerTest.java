/**
 * MassifSource
 * @year 2015
 * @author nsarychev
 */
package com.massifsource.sample.damp.ms.test.component.integration;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.springframework.beans.factory.annotation.Value;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.massifsource.sample.damp.common.JmsConfiguration;
import com.massifsource.sample.damp.ms.test.common.TestDataProvider;
import com.massifsource.sample.damp.ms.test.component.context.ComponentContextBase;

@Test(groups = "listener-tests", dependsOnGroups = "basic-tests")
public class ListenerTest extends ComponentContextBase {
        
    @Value("${jms.interface.port}")
    private String jmsPort;
    
    @Value("${jms.interface.hostname}")
    private String jmsHostname;
    
    @BeforeClass
    public void setUpListenerTest() throws ClientProtocolException, IOException {
        sourceRestService.configureJms(new JmsConfiguration(jmsHostname, jmsPort));
        assertEquals(sourceRestService.getCount("product_state"), 0);
        
    }
    
    @Test
    public void testAddRemoveListener() throws JsonParseException, JsonMappingException, IOException, InterruptedException {
        String id = sourceRestService.addListener("product_state");
        assertNotNull(id);
        
        TestDataProvider.populateProductState(5, sourceRestService, 8679);
        waitOnQueToBeEmpty();
        assertEquals(sourceRestService.getCount("product_state"), 5);
        assertEquals(destinationRestService.getCount("product_state"), 5);
        
        sourceRestService.delete("product_state", 8679);
        assertEquals(sourceRestService.getCount("product_state"), 4);
        waitOnQueToBeEmpty();
        assertEquals(destinationRestService.getCount("product_state"), 4);
        
        sourceRestService.removeListener("product_state", id);
        TestDataProvider.populateProductState(1, sourceRestService, 9679);
        assertEquals(sourceRestService.getCount("product_state"), 5);
        assertEquals(destinationRestService.getCount("product_state"), 4);
    }
    
    @Test
    public void testAddRemoveListenerWithNoId() throws JsonParseException, JsonMappingException, IOException, InterruptedException {
        sourceRestService.addListener("product_state");
        
        TestDataProvider.populateProductState(5, sourceRestService, 8679);
        waitOnQueToBeEmpty();
        assertEquals(sourceRestService.getCount("product_state"), 5);
        assertEquals(destinationRestService.getCount("product_state"), 5);
        
        sourceRestService.delete("product_state", 8679);
        waitOnQueToBeEmpty();
        assertEquals(sourceRestService.getCount("product_state"), 4);
        assertEquals(destinationRestService.getCount("product_state"), 4);
        
        sourceRestService.removeListener("product_state");
        TestDataProvider.populateProductState(1, sourceRestService, 9679);
        assertEquals(sourceRestService.getCount("product_state"), 5);
        assertEquals(destinationRestService.getCount("product_state"), 4);
    }
    
    @AfterClass
    public void cleanUp() throws ClientProtocolException, IOException {
        sourceRestService.deleteAll("product_state");
        destinationRestService.deleteAll("product_state");
    }

}

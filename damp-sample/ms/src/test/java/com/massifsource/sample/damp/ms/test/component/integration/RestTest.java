/**
 * MassifSource
 * @year 2015
 * @author nsarychev
 */
package com.massifsource.sample.damp.ms.test.component.integration;

import static org.testng.Assert.assertEquals;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.massifsource.sample.damp.ms.test.common.TestDataProvider;
import com.massifsource.sample.damp.ms.test.component.context.ComponentContextBase;

@Test(groups = "basic-tests" )
public class RestTest extends ComponentContextBase {
 
    @BeforeClass
    public void assertEmpty() throws ClientProtocolException, IOException {
        assertEquals(sourceRestService.getCount("product_state"), 0);
    }
    
    @Test
    public void testRestPut() throws JsonParseException, JsonMappingException, IOException, InterruptedException {
        
        TestDataProvider.populateProductState(5, sourceRestService, 8679);
        waitOnQueToBeEmpty();
        assertEquals(sourceRestService.getCount("product_state"), 5);
        assertEquals(destinationRestService.getCount("product_state"), 0);
    }
    
    @Test(dependsOnMethods = {"testRestPut"})
    public void testRestDelete() throws JsonParseException, JsonMappingException, IOException, InterruptedException {
        for (int i = 0; i < 5; i++) {
            int key = 8679 + i;
            sourceRestService.delete("product_state", String.valueOf(key));
        }
        waitOnQueToBeEmpty();
        assertEquals(sourceRestService.getCount("product_state"), 0);
    }
}

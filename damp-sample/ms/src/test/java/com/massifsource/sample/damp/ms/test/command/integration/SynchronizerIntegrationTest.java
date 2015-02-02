/**
 * MassifSource
 * @year 2015
 * @author nsarychev
 */
package com.massifsource.sample.damp.ms.test.command.integration;

import static org.testng.Assert.assertEquals;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.massifsource.sample.damp.ms.test.command.context.CommandContextBase;
import com.massifsource.sample.damp.ms.test.common.TestDataProvider;

@Test(groups = "synchronizer-command-tests", dependsOnGroups = "listener-command-tests")
public class SynchronizerIntegrationTest extends CommandContextBase {
        
    @BeforeClass
    public void prepareData() throws ClientProtocolException, IOException, InterruptedException {
        assertEquals(sourceRestService.getCount("product_state"), 0);
        assertEquals(destinationRestService.getCount("product_state"), 0);
        
        TestDataProvider.populateProductState(50, sourceRestService, 9679);
        
        assertEquals(sourceRestService.getCount("product_state"), 50);
        assertEquals(destinationRestService.getCount("product_state"), 0);
    
    }
    
    @Test
    public void testSyncAll() throws InterruptedException, IOException {
        commandSupportService.syncAll();
        assertEquals(destinationRestService.getCount("product_state"), 50);
    }
}

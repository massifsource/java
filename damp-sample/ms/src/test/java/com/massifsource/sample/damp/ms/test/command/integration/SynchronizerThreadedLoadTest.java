/**
 * MassifSource
 * @year 2015
 * @author nsarychev
 */
package com.massifsource.sample.damp.ms.test.command.integration;

import static org.testng.Assert.assertEquals;

import java.io.IOException;

import org.testng.annotations.Test;

@Test(groups = "synchronizer-command-threaded-load-tests", dependsOnGroups = "synchronizer-command-tests")
public class SynchronizerThreadedLoadTest extends LoadTestBase {

    @Test
    public void testSyncAll() throws InterruptedException, IOException {
        log.info("Started testSyncAll()");
        runSyncAll();
        assertEquals(destinationRestService.getCount("product_state"), sourceRestService
            .getCount("product_state"));
    }

}

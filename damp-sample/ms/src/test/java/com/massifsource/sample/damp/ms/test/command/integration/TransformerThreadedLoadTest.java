/**
 * MassifSource
 * @year 2015
 * @author nsarychev
 */
package com.massifsource.sample.damp.ms.test.command.integration;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

import java.io.IOException;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.massifsource.sample.damp.ms.cli.support.CliContextProvider;
import com.massifsource.sample.damp.ms.cli.support.CommandSupportService;
import com.massifsource.sample.damp.ms.transform.MapTransformer;

@Test(dependsOnGroups = "synchronizer-command-threaded-load-tests")
public class TransformerThreadedLoadTest extends LoadTestBase {

    @BeforeClass
    public void setUp() throws ClientProtocolException, IOException, InterruptedException {
        @SuppressWarnings("unchecked")
        Map<String, MapTransformer> mapNameToTransformerMap = CliContextProvider.getContext().getBean(
            "mapNameToTransformerMap", Map.class);
        MapTransformer productStateRenameAndAddTransformer = CliContextProvider.getContext().getBean(
            "productStateRenameAndAddTransformer", MapTransformer.class);
        assertNotNull(mapNameToTransformerMap.get("product_state"));
        mapNameToTransformerMap.put("product_state", productStateRenameAndAddTransformer);
        CommandSupportService.MAPS = new String[] { "product_state" };
    }

    @Test
    public void testTransformAndSyncAll() throws InterruptedException, IOException {
        log.info("Started testTransformAndSyncAll()");
        runSyncAll();
        assertEquals(destinationRestService.getCount("product_state"), sourceRestService
            .getCount("product_state"));
        log.info("finished testSyncAll()");
    }
}

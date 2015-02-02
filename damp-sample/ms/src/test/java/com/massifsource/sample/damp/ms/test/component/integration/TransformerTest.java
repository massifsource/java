/**
 * MassifSource
 * @year 2015
 * @author nsarychev
 */
package com.massifsource.sample.damp.ms.test.component.integration;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

import java.io.IOException;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.http.client.ClientProtocolException;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.massifsource.sample.damp.ms.test.common.TestDataProvider;
import com.massifsource.sample.damp.ms.test.component.context.ComponentContextBase;
import com.massifsource.sample.damp.ms.transform.MapTransformer;
import com.massifsource.sample.damp.ms.transform.custom.ProductStateRenameAndAddTransformer;

@Test(dependsOnGroups = "listener-tests")
public class TransformerTest extends ComponentContextBase {

    @Resource(name = "mapNameToTransformerMap")
    private Map<String, MapTransformer> mapNameToTransformerMap;

    @Autowired
    private ProductStateRenameAndAddTransformer productStateRenameAndAddTransformer;

    @BeforeClass
    public void setUpTransformerTest() {
        assertNotNull(mapNameToTransformerMap);
        mapNameToTransformerMap.put("product_state", productStateRenameAndAddTransformer);
        assertNotNull(mapNameToTransformerMap.get("product_state"));
        assertEquals(mapNameToTransformerMap.get("product_state").getClass(),
            ProductStateRenameAndAddTransformer.class);
    }

    @Test
    public void testTransformer() throws JsonParseException, JsonMappingException, IOException,
        InterruptedException {
        String id = sourceRestService.addListener("product_state");
        assertNotNull(id);
        TestDataProvider.populateProductState(5, sourceRestService, 8679);
        waitOnQueToBeEmpty();
        assertEquals(sourceRestService.getCount("product_state"), 5);
        assertEquals(destinationRestService.getCount("product_state"), 0);
        sourceRestService.delete("product_state", 8679);
        assertEquals(sourceRestService.getCount("product_state"), 4);
        assertEquals(destinationRestService.getCount("product_state"), 0);
        sourceRestService.removeListener("product_state", id);
        TestDataProvider.populateProductState(1, sourceRestService, 9679);
        assertEquals(sourceRestService.getCount("product_state"), 5);
        assertEquals(destinationRestService.getCount("product_state"), 0);
    }

    @AfterClass
    public void cleanUp() throws ClientProtocolException, IOException {
        sourceRestService.deleteAll("product_state");
        destinationRestService.deleteAll("product_state");
        assertEquals(sourceRestService.getCount("product_state"), 0);
        assertEquals(destinationRestService.getCount("product_state"), 0);
    }
}

/**
 * MassifSource
 * @year 2015
 * @author nsarychev
 */
package com.massifsource.sample.damp.das.test.integration;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.web.client.RestTemplate;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.massifsource.sample.damp.das.App;
import com.massifsource.sample.damp.das.domain.ProductState;
import com.massifsource.sample.damp.das.domain.ProductStatus;

@ContextConfiguration({ "classpath:spring/test-beans.xml" })
public class DasTest extends AbstractTestNGSpringContextTests {

    private static String BASE_REST_URL = "http://localhost:6555/";
    private static final String OBJECTS_DIR = "classpath:/objects/";
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private JsonHelper jsonHelper;
    private static ConfigurableApplicationContext context;

    @BeforeSuite
    public static void start() throws Exception {
        Future<ConfigurableApplicationContext> future = Executors.newSingleThreadExecutor().submit(
            new Callable<ConfigurableApplicationContext>() {
                String[] args = { "" };

                public ConfigurableApplicationContext call() {
                    App.main(args);
                    return App.getContext();
                }
            });
        context = future.get(60, TimeUnit.SECONDS);
    }

    @BeforeClass
    public void setUp() {
        assertNotNull(restTemplate);
    }

    @Test
    public void testCluster() throws JsonParseException, JsonMappingException, IOException {
        restTemplate.getForObject(BASE_REST_URL + "cluster/members", Object.class);
    }

    @Test
    public void testCreateAndRead() throws JsonParseException, JsonMappingException, IOException {
        Object object = jsonHelper.buildType(OBJECTS_DIR + "/product_state/9678.json",
            Object.class);
        restTemplate.postForObject(BASE_REST_URL + "maps/product_state", object, Object.class);
        Object response = restTemplate.getForObject(BASE_REST_URL + "maps/product_state/9678",
            Object.class);
        assertNotNull(response);
    }
    
    @Test
    public void testCreateNonClassSpecificObject() throws JsonParseException, JsonMappingException,
        IOException {
        ObjectMapper mapper = new ObjectMapper();
        JSONObject json = new JSONObject();
        json.put("productId", 9679L);
        json.put("ProductStateType", "NORMAL");
        Object obj = mapper.readValue(json.toString(), Object.class);
        restTemplate.postForObject(BASE_REST_URL + "maps/product_state", obj, Object.class);
        ProductState response = restTemplate.getForObject(BASE_REST_URL + "maps/product_state/9679",
            ProductState.class);
        assertEquals(response.getProductStatus(), ProductStatus.AVAILABLE);
    }
    
    @Test(dependsOnMethods = { "testCreateAndRead" })
    public void testDelete() {
        restTemplate.delete(BASE_REST_URL + "maps/product_state/9678");
        ProductState response = restTemplate.getForObject(BASE_REST_URL + "maps/product_state/9678",
            ProductState.class);
        assertEquals(response, null);
        
    }
    
    @Test
    public void testCreateList() throws JsonParseException, JsonMappingException, IOException  {
        @SuppressWarnings("unchecked")
        List<ProductState> list = jsonHelper.buildType(OBJECTS_DIR + "/product_state/list.json",
            List.class);
        restTemplate.postForObject(BASE_REST_URL + "maps/product_state/addlist", list, Object.class);
    }
    
    @Test
    public void testGetAllEntries() throws JsonParseException, JsonMappingException, IOException  {
        @SuppressWarnings("unchecked")
        List<ProductState> list = restTemplate.getForObject(BASE_REST_URL + "maps/product_state/allentries", List.class);
        assertNotNull(list);
        assertEquals(list.size(), 4);
    }
    
    @Test
    public void testEntryView() throws JsonParseException, JsonMappingException, IOException {
        ProductState ProductState = jsonHelper.buildType(OBJECTS_DIR + "/product_state/9678.json",
            ProductState.class);
        restTemplate.postForObject(BASE_REST_URL + "maps/product_state", ProductState, Object.class);
        restTemplate.getForObject(BASE_REST_URL + "maps/product_state/entryviews/9678", Object.class);
    }

    @AfterSuite
    public static void stop() {
        if (context != null) {
            context.close();
        }
    }

}

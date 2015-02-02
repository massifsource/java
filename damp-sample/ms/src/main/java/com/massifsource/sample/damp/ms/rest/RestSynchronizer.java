/**
 * MassifSource
 * @year 2015
 * @author nsarychev
 */
package com.massifsource.sample.damp.ms.rest;

import java.util.concurrent.CountDownLatch;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.massifsource.sample.damp.ms.transform.Transformer;

@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@Component
public class RestSynchronizer implements Runnable {

    private static final Logger log = LoggerFactory.getLogger(RestSynchronizer.class);

    @Autowired
    private RestService destinationRestService;

    @Autowired
    private RestService sourceRestService;

    @Autowired
    private Transformer transformer;

    private CountDownLatch latch;

    private String mapName;

    public String getMapName() {
        return mapName;
    }

    public void setMapName(String mapName) {
        this.mapName = mapName;
    }

    public RestSynchronizer() {
    }

    public void setLatch(CountDownLatch countDownLatch) {
        this.latch = countDownLatch;
    }

    public void run() {
        log.trace("Synchronizing map [{}]", mapName);
        try {
            String transformedMapName = transformer.transformMapName(mapName);
            destinationRestService.deleteAll(transformedMapName);
            log.trace("Deleted all records from map [{}]", mapName);
            JSONArray allRecords = sourceRestService.getAll(mapName);
            log.trace("Read all [{}] records from map [{}]", allRecords.length(), mapName);
            log.trace("Transforming all [{}] records from map [{}]", allRecords.length(), mapName);
            for (int i = 0; i < allRecords.length(); i++) {
                JSONObject object = allRecords.getJSONObject(i);
                JSONObject transformedObject = transformer.transformObject(mapName, object);
                destinationRestService.put(transformedMapName, transformedObject);
                log.trace("Transformed and added [{}] record from map [{}]", object, mapName);
            }
            log.trace("[{}] records added to map [{}]", allRecords.length(), mapName);
        }
        catch (Exception e1) {
            throw new RestSynchronizerException(e1);
        }
        finally {
            latch.countDown();
        }
    }

    public CountDownLatch getLatch() {
        return latch;
    }
}

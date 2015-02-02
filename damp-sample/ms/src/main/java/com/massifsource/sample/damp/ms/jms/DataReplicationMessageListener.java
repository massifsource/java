/**
 * MassifSource
 * @year 2015
 * @author nsarychev
 */
package com.massifsource.sample.damp.ms.jms;

import java.util.concurrent.CountDownLatch;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.massifsource.sample.damp.common.DataReplicationRequest;
import com.massifsource.sample.damp.common.DataReplicationResponse;
import com.massifsource.sample.damp.ms.rest.RestService;
import com.massifsource.sample.damp.ms.transform.Transformer;

@Component
public class DataReplicationMessageListener {

    Logger log = LoggerFactory.getLogger(DataReplicationMessageListener.class);

    @Qualifier("destinationRestService")
    @Autowired
    private RestService destinationRestService;
    
    private CountDownLatch latch;
    
    @Autowired
    private Transformer transformer;

    public DataReplicationResponse process(DataReplicationRequest request) throws Exception {
        log.debug("Received request [{}]", request.toString());
        // wait for sync to complete
        if (latch != null) latch.await();
        log.debug("Done waiting to sync [{}]", request.toString());
        //TODO add return of responses
        String transformedMapname = transformer.transformMapName(request.getMapName());
        switch (request.getOperation()) {
            case PUT :
                JSONObject transformedObject = transformer.transformObject(request.getMapName(), new JSONObject(request.getContent()));
                destinationRestService.put(transformedMapname, transformedObject);
                break;
            case DELETE : 
                String transformedKey = transformer.transformKey(request.getMapName(),  request.getContent());
                destinationRestService.delete(transformedMapname, transformedKey);
                break;
            default : 
                return null;
        }
        return new DataReplicationResponse();
    }

    public CountDownLatch getLatch() {
        return latch;
    }

    public void setLatch(CountDownLatch latch) {
        this.latch = latch;
    }

}

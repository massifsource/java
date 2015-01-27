/**
 * MassifSource
 * @year 2015
 * @author nsarychev
 */
package com.massifsource.sample.damp.das.jms;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.massifsource.sample.damp.common.DataReplicationResponse;

@Component
public class ResponseListener {
    Logger log = LoggerFactory.getLogger(ResponseListener.class);
    
    public void processResponse (DataReplicationResponse response) {
        log.trace("Received response [{}]", response);
    }

}

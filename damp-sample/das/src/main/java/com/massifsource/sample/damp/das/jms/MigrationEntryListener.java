/**
 * MassifSource
 * @year 2015
 * @author nsarychev
 */
package com.massifsource.sample.damp.das.jms;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hazelcast.core.EntryEvent;
import com.hazelcast.core.EntryListener;
import com.hazelcast.core.MapEvent;
import com.massifsource.sample.damp.common.DataReplicationRequest;
import com.massifsource.sample.damp.common.Operation;

@Component
public class MigrationEntryListener implements EntryListener<Object, Object> {
    
    @Autowired
    private JmsTemplate jmsTemplate;
    
    private Logger log = LoggerFactory.getLogger(MigrationEntryListener.class);

    public void entryAdded(EntryEvent<Object, Object> event) {
        log.trace("Entry added [{}]", event);
        jmsTemplate.convertAndSend(new DataReplicationRequest(event.getName(), getObjectAsString(event.getValue()), Operation.PUT));
    }

    public void entryRemoved(EntryEvent<Object, Object> event) {
        log.trace("Entry Removed [{}]", event);
        jmsTemplate.convertAndSend(new DataReplicationRequest(event.getName(), getObjectAsString(event.getKey()), Operation.DELETE));
    }

    public void entryUpdated(EntryEvent<Object, Object> event) {
        log.trace("Entry updated [{}]", event);
        jmsTemplate.convertAndSend(new DataReplicationRequest(event.getName(), getObjectAsString(event.getValue()), Operation.PUT));
    }

    public void entryEvicted(EntryEvent<Object, Object> event) {
        log.trace("Entry evicted [{}]", event);
        jmsTemplate.convertAndSend(new DataReplicationRequest(event.getName(), getObjectAsString(event.getKey()), Operation.DELETE));
    }
    
    public String getObjectAsString(Object object) {
        ObjectMapper mapper = new ObjectMapper();
        String result = null;
        try {
            result = mapper.writeValueAsString(object);
        }
        catch (Exception e) {
            log.warn(e.getMessage());
        }
        return result;
    }

	public void mapCleared(MapEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void mapEvicted(MapEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}

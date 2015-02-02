/**
 * MassifSource
 * @year 2015
 * @author nsarychev
 */
package com.massifsource.sample.damp.das.test.integration;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class JsonHelper {

    private Logger log = LoggerFactory.getLogger(JsonHelper.class);

    @Autowired
    private ApplicationContext applicationContext;

    public <T> T buildType(String jsonFileName, Class<T> type) throws JsonParseException,
        JsonMappingException, IOException {
        ObjectMapper mapper = new ObjectMapper();
        Resource source = applicationContext.getResource(jsonFileName);
        T object = null;
        object = mapper.readValue(source.getInputStream(), type);

        return object;
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

}

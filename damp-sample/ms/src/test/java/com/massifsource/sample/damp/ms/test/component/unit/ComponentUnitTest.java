/**
 * MassifSource
 * @year 2015
 * @author nsarychev
 */
package com.massifsource.sample.damp.ms.test.component.unit;

import static org.testng.Assert.assertEquals;

import java.io.IOException;

import org.testng.annotations.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.massifsource.sample.damp.ms.test.component.context.ComponentContextBase;

public class ComponentUnitTest extends ComponentContextBase {
    
    @Test
    public void testUnit() throws JsonParseException, JsonMappingException, IOException, InterruptedException {
        assertEquals(0, 0);
    }

}

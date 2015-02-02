/**
 * MassifSource
 * @year 2015
 * @author nsarychev
 */
package com.massifsource.sample.damp.ms.test.common;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONObject;

import com.massifsource.sample.damp.ms.rest.RestService;

public class TestDataProvider {

    public static void populateProductState(int numberOfRecords, RestService service, int startingId) throws ClientProtocolException, IOException {
        for (int i = 0; i < numberOfRecords; i++) {
            JSONObject json = new JSONObject();
            json.put("productId", (startingId + i));
            json.put("productState", "AVAILABLE");
            service.put("product_state", json);
        }
    }
       
}

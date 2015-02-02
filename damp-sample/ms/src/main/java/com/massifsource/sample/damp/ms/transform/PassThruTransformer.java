/**
 * MassifSource
 * @year 2015
 * @author nsarychev
 */
package com.massifsource.sample.damp.ms.transform;

import org.json.JSONObject;
import org.springframework.stereotype.Component;

@Component
public class PassThruTransformer implements MapTransformer {
        
    public JSONObject transformObject(JSONObject object) {
        return object;
    }
  
    public String transformKey(String key) {
        return key;
    }

    public String transformMapName(String mapName) throws Exception {
        return mapName;
    }
    
    

}

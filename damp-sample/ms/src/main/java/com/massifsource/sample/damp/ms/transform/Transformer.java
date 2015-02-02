/**
 * MassifSource
 * @year 2015
 * @author nsarychev
 */
package com.massifsource.sample.damp.ms.transform;

import org.json.JSONObject;
import org.springframework.stereotype.Service;

@Service
public interface Transformer {

    public JSONObject transformObject(String mapName, JSONObject object) throws Exception;
    
    public String transformKey(String mapName, String key) throws Exception;
    
    public String transformMapName(String mapName) throws Exception;
}

/**
 * MassifSource
 * @year 2015
 * @author nsarychev
 */
package com.massifsource.sample.damp.ms.transform;

import org.json.JSONObject;

public interface MapTransformer {

    public JSONObject transformObject(JSONObject object) throws Exception;
    
    public String transformKey(String key) throws Exception;

    public String transformMapName(String mapName) throws Exception;
}

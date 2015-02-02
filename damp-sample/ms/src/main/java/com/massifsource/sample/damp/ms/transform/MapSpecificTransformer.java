/**
 * MassifSource
 * @year 2015
 * @author nsarychev
 */
package com.massifsource.sample.damp.ms.transform;

import java.util.Map;

import javax.annotation.Resource;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MapSpecificTransformer implements Transformer {

    @Resource(name = "mapNameToTransformerMap")
    private Map<String, MapTransformer> mapNameToTransformerMap;

    @Autowired
    private MapTransformer passThruTransformer;

    public JSONObject transformObject(String mapName, JSONObject object) throws Exception {
        MapTransformer mapTransformer = mapNameToTransformerMap.get(mapName);
        if (mapTransformer == null) {
            return passThruTransformer.transformObject(object);
        }
        else {
            return mapTransformer.transformObject(object);
        }
    }

    public String transformKey(String mapName, String key) throws Exception {
        MapTransformer mapTransformer = mapNameToTransformerMap.get(mapName);
        if (mapTransformer == null) {
            return passThruTransformer.transformKey(key);
        }
        else {
            return mapTransformer.transformKey(key);
        }
    }

    public String transformMapName(String mapName) throws Exception {
        MapTransformer mapTransformer = mapNameToTransformerMap.get(mapName);
        if (mapTransformer == null) {
            return passThruTransformer.transformMapName(mapName);
        }
        else {
            return mapTransformer.transformMapName(mapName);
        }
    }

}

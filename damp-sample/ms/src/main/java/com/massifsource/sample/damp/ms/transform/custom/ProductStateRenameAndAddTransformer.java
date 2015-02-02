/**
 * MassifSource
 * @year 2015
 * @author nsarychev
 */
package com.massifsource.sample.damp.ms.transform.custom;

import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.massifsource.sample.damp.ms.transform.MapTransformer;

@Component
public class ProductStateRenameAndAddTransformer implements MapTransformer {
        
    public JSONObject transformObject(JSONObject jsonObject) throws Exception {
        JSONObject transformedJson = new JSONObject();
        transformedJson.put("productId", jsonObject.get("productId"));
        transformedJson.put("productState", "COMING_SOON");
        return transformedJson;
    }

    public String transformKey(String key) throws Exception {
        return key;
    }

    public String transformMapName(String mapName) throws Exception {
        return "new_product_state";
    }

}

/**
 * MassifSource
 * @year 2015
 * @author nsarychev
 */
package com.massifsource.sample.damp.ms.rest;

import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.io.CharStreams;
import com.massifsource.sample.damp.common.JmsConfiguration;

public class RestService {

    private RestUrlProvider restUrlProvider;

    private Logger log = LoggerFactory.getLogger(RestService.class);

    private static final String CONTENT_TYPE_JSON = "application/json";

    private HttpClient httpClient = HttpClients.createMinimal();

    public void put(String mapName, JSONObject object) throws ClientProtocolException, IOException {
        log.trace("Executing a PUT for map [{}] and object[{}]", mapName, object);
        String entityContent = post(mapName, "", object);
        log.trace("Recieved response for PUT [{}]", entityContent);
    }

    public void putList(String mapName, JSONArray objects) throws ClientProtocolException,
        IOException {
        log.trace("Executing a PUTLIST for map [{}] and list of objects[{}]", mapName, objects);
        String entityContent = post(mapName, "/addlist", objects);
        log.trace("Recieved response for PUTLIST [{}]", entityContent);
    }

    public void delete(String mapName, Object key) throws ClientProtocolException, IOException {
        log.trace("Executing a DELETE for map [{}] and key[{}]", mapName, key);
        String entityContent = delete(mapName, "/" + key.toString());
        log.trace("Recieved response for DELETE [{}]", entityContent);
    }

    public void deleteAll(String mapName) throws ClientProtocolException, IOException {
        log.trace("Executing a DELETEALL for map [{}] ", mapName);
        String entityContent = delete(mapName, "/allentries");
        log.trace("Recieved response for DELETEALL [{}]", entityContent);
    }

    public JSONArray getAll(String mapName) throws ClientProtocolException, IOException {
        log.trace("Executing a READALL for map [{}]", mapName);
        String entityContent = get(mapName, "/allentries");
        log.trace("Recieved response for READALL [{}]", entityContent);
        return new JSONArray(entityContent);
    }
    
    public int getCount(String mapName) throws ClientProtocolException, IOException {
        log.trace("Executing a READALL for map [{}]", mapName);
        String entityContent = get(mapName, "/count");
        log.trace("Recieved response for READALL [{}]", entityContent);
        return Integer.valueOf(entityContent);
    }

    public  String addListener (String mapName) throws ClientProtocolException, IOException {
        log.trace("Adding a listener for map [{}]", mapName);
        String entityContent = post(mapName, "/migration/listener", null);
        JSONObject jsonResponseObject = new JSONObject(entityContent);
        log.trace("Listener [{}] for map [{}] added",jsonResponseObject, mapName);
        return jsonResponseObject.getString("id");
    }
    
    public  void configureJms (JmsConfiguration listenerConfiguration) throws ClientProtocolException, IOException {
        log.trace("Configuring JMS [{}]", listenerConfiguration);
        String entityContent = post(restUrlProvider.getRestUrlBase() + "/migration/jms", new JSONObject(listenerConfiguration));
        log.trace("JMS configure response: {}",entityContent);
    }

    public void removeListener(String mapName, String listenerId) throws ClientProtocolException,
        IOException {
        log.trace("Removing a listener with id [{}] for map [{}]", listenerId, mapName);
        String entityContent = delete(mapName, "/migration/listener/" + listenerId);
        log.trace("Recieved response for REMOVELISTENER [{}]", entityContent);
    }

    public void removeListener(String mapName) throws ClientProtocolException, IOException {
        log.trace("Removing a listener for map [{}]", mapName);
        String entityContent = delete(mapName, "/migration/listener");
        log.trace("Recieved response for REMOVELISTENER [{}]", entityContent);
    }

    public RestUrlProvider getRestUrlProvider() {
        return restUrlProvider;
    }

    public void setRestUrlProvider(RestUrlProvider restUrlProvider) {
        this.restUrlProvider = restUrlProvider;
    }

    private String post(String mapName, String extraUrlParameters, Object body)
        throws ClientProtocolException, IOException {
        return post(restUrlProvider.getRestUrlBaseAndMapName(mapName)
            + extraUrlParameters, body);
    }
    
    private String post(String url, Object body)
        throws ClientProtocolException, IOException {
        log.trace("Performing a POST for URL [{}]", url);
        HttpPost post = new HttpPost(url);
        if (body != null) {
            StringEntity input = new StringEntity(body.toString());
            input.setContentType(CONTENT_TYPE_JSON);
            post.setEntity(input);
        }
        HttpResponse response = httpClient.execute(post);
        return CharStreams.toString(new InputStreamReader(response.getEntity().getContent(),
            "UTF-8"));
    }

    private String get(String mapName, String extraUrlParameters) throws ClientProtocolException,
        IOException {
        HttpGet get = new HttpGet(restUrlProvider.getRestUrlBaseAndMapName(mapName)
            + extraUrlParameters);
        HttpResponse response = httpClient.execute(get);
        return CharStreams.toString(new InputStreamReader(response.getEntity().getContent(),
            "UTF-8"));
    }

    private String delete(String mapName, String extraUrlParameters)
        throws ClientProtocolException, IOException {
        HttpDelete delete = new HttpDelete(restUrlProvider.getRestUrlBaseAndMapName(mapName)
            + extraUrlParameters);
        HttpResponse response = httpClient.execute(delete);
        return CharStreams.toString(new InputStreamReader(response.getEntity().getContent(),
            "UTF-8"));
    }

}

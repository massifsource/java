/**
 * MassifSource
 * @year 2015
 * @author nsarychev
 */
package com.massifsource.sample.damp.ms.cli.support;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.concurrent.CountDownLatch;

import org.apache.http.client.ClientProtocolException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.massifsource.sample.damp.common.JmsConfiguration;
import com.massifsource.sample.damp.ms.rest.RestSynchronizer;

@Component
public class CommandSupportService {

    private static final String SOURCE_REST_HOSTNAME_PROPERTY_NAME = "source.rest.hostname";

    private static final String SOURCE_REST_PORT_PROPERTY_NAME = "source.rest.port";

    private static final String DESTINATION_REST_HOSTNAME_PROPERTY_NAME = "destination.rest.hostname";

    private static final String DESTINATION_REST_PORT_PROPERTY_NAME = "destination.rest.port";

    private static final String JMS_HOSTNAME_PROPERTY_NAME = "jms.interface.hostname";

    private static final String JMS_PORT_PROPERTY_NAME = "jms.interface.port";

    private static final String DEFAULT_ENV_PROPERTIES_FILENAME = "conf/env.properties";
    private static final Logger log = LoggerFactory.getLogger(CommandSupportService.class);

    public static String[] MAPS = { "product_state", "subscription" };

    private boolean initialized = false;

    public boolean isInitialized() {
        return initialized;
    }

    public String loadConfigurationFile(String filePath) throws IOException {
        Properties properties = new Properties();
        String fileUsed = null;
        InputStream is;
        if (filePath != null && !filePath.trim().isEmpty()) {
            is = new FileInputStream(filePath);
            properties.load(is);
            fileUsed = filePath;
            is.close();
        }
        else {
            fileUsed = DEFAULT_ENV_PROPERTIES_FILENAME;
            is = Thread.currentThread().getContextClassLoader()
                .getResourceAsStream(fileUsed);
            if (is == null) {
                fileUsed = System.getProperty("basedir") + "/" + DEFAULT_ENV_PROPERTIES_FILENAME;
                is = new FileInputStream(fileUsed);
            }
            properties.load(is);
            is.close();
            fileUsed = DEFAULT_ENV_PROPERTIES_FILENAME;
        }
        for (Object key : properties.keySet()) {
            System.setProperty(key.toString(), properties.getProperty(key.toString()));
        }
        return "Loaded " + fileUsed;
    }

    public String configureRestSource(String host, int port) {
        System.setProperty(SOURCE_REST_HOSTNAME_PROPERTY_NAME, host);
        System.setProperty(SOURCE_REST_PORT_PROPERTY_NAME, String.valueOf(port));
        return String.format("REST source is configured.\nhost: %s\nport: %s", host, port);
    }

    public String configureRestDestination(String host, int port) {
        System.setProperty(DESTINATION_REST_HOSTNAME_PROPERTY_NAME, host);
        System.setProperty(DESTINATION_REST_PORT_PROPERTY_NAME, String.valueOf(port));
        return String.format("REST destination is configured.\nhost: %s\nport: %s", host, port);
    }

    public String configureJms(String host, int port) {
        System.setProperty(JMS_HOSTNAME_PROPERTY_NAME, host);
        System.setProperty(JMS_PORT_PROPERTY_NAME, String.valueOf(port));
        return String.format("JMS is configured.\nhost: %s\nport: %s", host, port);
    }

    public boolean checkConfig() throws Exception {
        if (!isPropertySet(SOURCE_REST_HOSTNAME_PROPERTY_NAME)
            || !isPropertySet(SOURCE_REST_PORT_PROPERTY_NAME))
            throw new Exception(
                "REST source is not configured, use 'configure rest source' command");
        if (!isPropertySet(DESTINATION_REST_HOSTNAME_PROPERTY_NAME)
            || !isPropertySet(DESTINATION_REST_PORT_PROPERTY_NAME))
            throw new Exception(
                "REST destination is not configured, use 'configure rest destination' command");
        if (!isPropertySet(JMS_HOSTNAME_PROPERTY_NAME) || !isPropertySet(JMS_PORT_PROPERTY_NAME))
            throw new Exception("JMS is not configured, use 'configure jms' command");
        return true;
    }

    public String initializeContext() throws IOException {
        try {
            checkConfig();
        }
        catch (Exception e) {
            log.info("Loading defaults");
            loadConfigurationFile(null);
        }
        try {
            if (checkConfig()) {
                ApplicationContext context = CliContextProvider.getContext();
                if (context != null) {
                    CliContextProvider.getSourceRestService().configureJms(
                        new JmsConfiguration(System.getProperty(JMS_HOSTNAME_PROPERTY_NAME), System
                            .getProperty(JMS_PORT_PROPERTY_NAME)));
                    initialized = true;
                    log.info(displayConfig());
                    return "Context initialized";
                }
            }
        }
        catch (Exception e) {
            log.info(e.getMessage());
        }
        return null;
    }

    public String addListener(String mapName) throws ClientProtocolException, IOException {
        if (!initialized) return "Connections are not initialized";
        return CliContextProvider.getSourceRestService().addListener(mapName);
    }

    public String removeListener(String mapName, String id) throws ClientProtocolException,
        IOException {
        if (!initialized) return "Connections are not initialized";
        if (id == null) {
            CliContextProvider.getSourceRestService().removeListener(mapName);
        }
        else {
            CliContextProvider.getSourceRestService().removeListener(mapName, id);
        }
        return "Listener was removed for map " + mapName;
    }

    public String syncMap(String mapName) throws InterruptedException, ClientProtocolException,
        IOException {
        if (!initialized) return "Connections are not initialized";
        CountDownLatch latch = new CountDownLatch(1);
        CliContextProvider.getDataReplicationMessageListener().setLatch(latch);
        processSyncMap(mapName, latch);
        while (latch.getCount() > 0) {
            Thread.sleep(1000);
        }
        return "Sync completed for map " + mapName;
    }

    private void processSyncMap(String mapName, CountDownLatch latch) throws InterruptedException,
        ClientProtocolException, IOException {
        log.info(removeListener(mapName, null));
        log.info("Added listener with id [{}] to map [{}]\n", addListener(mapName), mapName);
        RestSynchronizer restSynchronizer = CliContextProvider.getRestSynchronizer();
        restSynchronizer.setLatch(latch);
        restSynchronizer.setMapName(mapName);
        Thread thread = new Thread(restSynchronizer);
        thread.start();
        log.info(String.format("Running sync for map %s\n", mapName));
    }

    public String syncAll() throws InterruptedException, IOException {
        if (!initialized) return "Connections are not initialized";
        CountDownLatch latch = new CountDownLatch(MAPS.length);
        CliContextProvider.getDataReplicationMessageListener().setLatch(latch);
        for (int i = 0; i < MAPS.length; i++) {
            processSyncMap(MAPS[i], latch);
        }
        while (latch.getCount() > 0) {
            Thread.sleep(1000);
        }
        return "Sync completed for all maps";
    }

    public String removeAllListeners() throws IOException {
        if (!initialized) return "Connections are not initialized";
        for (int i = 0; i < MAPS.length; i++) {
            log.info(removeListener(MAPS[i], null));
        }
        return "Removed all listeners";
    }

    private boolean isPropertySet(String propertyName) {
        if (System.getProperty(propertyName) == null) return false;
        if (System.getProperty(propertyName).trim().isEmpty()) return false;
        return true;
    }

    public String displayConfig() {
        StringBuffer sb = new StringBuffer("\n");
        sb.append(SOURCE_REST_HOSTNAME_PROPERTY_NAME).append(": ").append(System.getProperty(SOURCE_REST_HOSTNAME_PROPERTY_NAME)).append("\n");
        sb.append(SOURCE_REST_PORT_PROPERTY_NAME).append(": ").append(System.getProperty(SOURCE_REST_PORT_PROPERTY_NAME)).append("\n");
        sb.append(DESTINATION_REST_HOSTNAME_PROPERTY_NAME).append(": ").append(System.getProperty(DESTINATION_REST_HOSTNAME_PROPERTY_NAME)).append("\n");
        sb.append(DESTINATION_REST_PORT_PROPERTY_NAME).append(": ").append(System.getProperty(DESTINATION_REST_PORT_PROPERTY_NAME)).append("\n");
        sb.append(JMS_HOSTNAME_PROPERTY_NAME).append(": ").append(System.getProperty(JMS_HOSTNAME_PROPERTY_NAME)).append("\n");
        sb.append(JMS_PORT_PROPERTY_NAME).append(": ").append(System.getProperty(JMS_PORT_PROPERTY_NAME));
        return sb.toString();
    }

}

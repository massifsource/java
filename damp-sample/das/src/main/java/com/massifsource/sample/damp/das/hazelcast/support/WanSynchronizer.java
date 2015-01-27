/**
 * MassifSource
 * @year 2015
 * @author nsarychev
 */
package com.massifsource.sample.damp.das.hazelcast.support;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.net.InetAddresses;
import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.config.Config;
import com.hazelcast.config.MapConfig;
import com.hazelcast.config.WanReplicationConfig;
import com.hazelcast.config.WanTargetClusterConfig;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import com.massifsource.sample.damp.das.hazelcast.Synchronizer;

@Component
public class WanSynchronizer implements Synchronizer {

    @Autowired
    private HazelcastInstance instance;
    private boolean constructed = false;
    private Map<String, String> mapNameToWanReplicationNameMap;
    private Map<String, HazelcastInstance> wanReplicationNameToClientMap;
    private static final Logger log = LoggerFactory.getLogger(WanSynchronizer.class);
    
    protected void buildClient() {
        mapNameToWanReplicationNameMap = new HashMap<String, String>();
        wanReplicationNameToClientMap = new HashMap<String, HazelcastInstance>();
        Config rootConfig = instance.getConfig();
        if (rootConfig == null) {
            return;
        }
        Map<String, MapConfig> mapConfigs = rootConfig.getMapConfigs();
        if (mapConfigs == null) {
            return;
        }
        for (Entry<String, MapConfig> mapConfig : mapConfigs.entrySet()) {
            if (mapConfig.getValue().getWanReplicationRef() != null) {
                String mapName = mapConfig.getValue().getName();
                String wanReplicationName = mapConfig.getValue().getWanReplicationRef().getName();
                mapNameToWanReplicationNameMap.put(mapName, wanReplicationName);
                log.debug("Assigned {} to {} map", wanReplicationName, mapName);
            }
        }
        for (Entry<String, WanReplicationConfig> wanConfig : rootConfig.getWanReplicationConfigs()
            .entrySet()) {
            WanReplicationConfig wanConfiguration = wanConfig.getValue();
            for (WanTargetClusterConfig config : wanConfiguration.getTargetClusterConfigs()) {
                ClientConfig clientConfig = new ClientConfig();
                clientConfig.getGroupConfig().setName(config.getGroupName())
                    .setPassword(config.getGroupPassword());
                List<String> endpoints = config.getEndpoints();
                List<String> usableEndpoints = new ArrayList<String>();
                for (String endpoint : endpoints) {
                    String[] ipAndPort = endpoint.split(":");
                    if (ipAndPort.length > 0 && InetAddresses.isInetAddress(ipAndPort[0])) {
                        usableEndpoints.add(endpoint);
                    }
                }
                if (usableEndpoints.isEmpty()) {
                    continue;
                }
                clientConfig.getNetworkConfig()
                    .addAddress(usableEndpoints.toArray(new String[usableEndpoints.size()]));
                try {
                    log.debug("Attempting to connect to WAN nodes {}",
                        clientConfig.getNetworkConfig().getAddresses());
                    HazelcastInstance client = HazelcastClient.newHazelcastClient(clientConfig);
                    if (client != null) {
                        wanReplicationNameToClientMap.put(wanConfiguration.getName(), client);
                        log.debug("Added client for WanReplication [{}]",
                            wanConfiguration.getName());
                        break;
                    }
                }
                catch (RuntimeException e) {
                    log.error("Couldn't connect to WAN cluster {}, due to {}, message: {} ",
                        clientConfig.getNetworkConfig().getAddresses(), e.getClass().getName(), e.getMessage());
                    log.trace(e.getMessage(), e);
                }
            }
        }
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public void synchronizeCache(IMap cache) {
        if (!constructed) {
            buildClient();
            constructed = true;
        }
        String replicationName = mapNameToWanReplicationNameMap.get(cache.getName());
        log.debug("Synchronizing [{}] for replication [{}]", cache.getName(), replicationName);
        if (replicationName == null) {
            log.debug("No WAN replication configuration is defined for {} ", cache.getName());
            return;
        }
        log.trace("Retreving client for replication [{}]", replicationName);
        HazelcastInstance client = wanReplicationNameToClientMap.get(replicationName);
        log.trace("Verifying client for replication [{}]", replicationName);
        try {
            if (client != null) {
                log.trace("Client found for replication [{}]", replicationName);
                IMap map = client.getMap(cache.getName());
                log.trace("Map found for cache [{}]", cache.getName());
                cache.putAll(map);
                log.trace("Data loaded for cache [{}]", cache.getName());
            }
            else {
                log.warn("No client found for replication [{}]", replicationName);
            }
        } catch (Exception e) {
            log.error("WAN Synchronizer Exception occured. ", e);
        }
    }

    public HazelcastInstance getInstance() {
        return instance;
    }

    public void setInstance(HazelcastInstance instance) {
        this.instance = instance;
    }

}

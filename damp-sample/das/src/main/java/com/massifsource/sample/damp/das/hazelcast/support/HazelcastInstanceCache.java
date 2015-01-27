/**
 * MassifSource
 * @year 2015
 * @author nsarychev
 */
package com.massifsource.sample.damp.das.hazelcast.support;

import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.hazelcast.core.ISet;
import com.massifsource.sample.damp.das.hazelcast.HazelcastCache;
import com.massifsource.sample.damp.das.hazelcast.Synchronizer;

public class HazelcastInstanceCache<K extends Serializable, V extends Serializable> extends BaseHazelcastCache<K, V>implements
    HazelcastCache<K, V> {

    public enum Status {
        SYNCHRONIZING, AVAILABLE
    }

    @Autowired
    private Synchronizer synchronizer;
    //private IMap<K, V> map;
    private boolean synchronizeCache = false;
    private String statusSetName;
    protected ISet<Status> statusSet;

    private static final Logger log = LoggerFactory.getLogger(HazelcastInstanceCache.class);

    public void setup() {
        super.setup();
        if (synchronizeCache) {
            this.statusSetName = "status_" + mapName;
            this.statusSet = instanceProvider.getInstance().getSet(this.statusSetName);
            if (statusSet.isEmpty()) {
                log.debug(
                    "[{}] - Node is the first node in the cluster, synchronization will occur.",
                    mapName);
                statusSet.add(Status.SYNCHRONIZING);
                synchronizer.synchronizeCache(getMap());
                statusSet.add(Status.AVAILABLE);
                statusSet.remove(Status.SYNCHRONIZING);
            }
            else {
                boolean wait = true;
                boolean first = true;
                log.debug("[{}] - Synchronization isn't needed", mapName);
                while (wait) {
                    switch (statusSet.iterator().next()) {
                    case SYNCHRONIZING:
                        if (first) log.debug("[{}] - Waiting on synchronizing node", mapName);
                        break;
                    default:
                        if (!first) log.debug("[{}] - Synchronizing node is finished", mapName);
                        wait = false;
                        break;
                    }
                    first = false;
                }
            }
        }
    }

    public boolean isSynchronizeCache() {
        return synchronizeCache;
    }

    public void setSynchronizeCache(boolean synchronizeCache) {
        this.synchronizeCache = synchronizeCache;
    }

    public Synchronizer getSynchronizer() {
        return synchronizer;
    }

    public void setSynchronizer(Synchronizer synchronizer) {
        this.synchronizer = synchronizer;
    }

}

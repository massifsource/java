/**
 * MassifSource
 * @year 2015
 * @author nsarychev
 */
package com.massifsource.sample.damp.das.web;

import java.io.Serializable;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.hazelcast.core.EntryView;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.monitor.LocalMapStats;
import com.massifsource.sample.damp.common.ListenerInformation;
import com.massifsource.sample.damp.das.dao.Dao;
import com.massifsource.sample.damp.das.domain.CacheableEntity;
import com.massifsource.sample.damp.das.jms.MigrationContextIsNullException;
import com.massifsource.sample.damp.das.jms.MigrationContextProvider;

public abstract class BaseCrudController<K extends Serializable, V extends CacheableEntity<K>> {
    
    protected Logger log = LoggerFactory.getLogger(BaseCrudController.class);

    @Autowired
    protected HazelcastInstance hazelcast;

    protected volatile String listenerId;

    @RequestMapping(method = RequestMethod.POST)
    public void putEntry(@RequestBody
    V body) {
        log.trace("Received a putEntry request for {}", body.toString());
        getDao().create(body);
    }

    @RequestMapping(value = "/addlist", method = RequestMethod.POST)
    public void putList(@RequestBody
    List<V> body) {
        getDao().create(body);
    }

    @RequestMapping(value = "/{key}", method = RequestMethod.GET)
    public V getEntry(@ModelAttribute("key")
    K key) {
        return getDao().read(key);
    }

    @RequestMapping(value = "/{key}", method = RequestMethod.DELETE)
    public void deleteEntry(@ModelAttribute("key")
    K key) {
        getDao().delete(key);
    }

    @RequestMapping(value = "/entryviews/{key}", method = RequestMethod.GET)
    public EntryView<Object, Object> getEntryView(@ModelAttribute("key")
    K key) {
        return hazelcast.getMap(getDao().getMapName()).getEntryView(key);
    }

    @RequestMapping(value = "/allentries", method = RequestMethod.GET)
    public List<V> getAllEntries() {
        return getDao().read();
    }
    
    @RequestMapping(value = "/count", method = RequestMethod.GET)
    public Integer getCount() {
        return getDao().count();
    }
    
    @RequestMapping(value = "/allentries", method = RequestMethod.DELETE)
    public void deleteAllEntries() {
        getDao().deleteAll();
    }

    @RequestMapping(value = "/stats", method = RequestMethod.GET)
    public LocalMapStats getStats() {
        return hazelcast.getMap(getDao().getMapName()).getLocalMapStats();
    }

    @RequestMapping(value = "/migration/listener", method = RequestMethod.POST)
    public ListenerInformation addListener() throws BeansException, MigrationContextIsNullException {
        if (listenerId != null && !listenerId.trim().isEmpty()) {
            boolean removed = removeListener(listenerId);
            if(removed) {
                listenerId = null;
            }   
        }
        listenerId = hazelcast.getMap(getDao().getMapName()).addEntryListener(
            MigrationContextProvider.getEntryListener(), true);
        return new ListenerInformation(listenerId);
    }

    @RequestMapping(value = "/migration/listener", method = RequestMethod.DELETE)
    public boolean removeListener() {
        if (listenerId == null) return false;
        return hazelcast.getMap(getDao().getMapName()).removeEntryListener(listenerId);
    }

    @RequestMapping(value = "/migration/listener/{id}", method = RequestMethod.DELETE)
    public boolean removeListener(@ModelAttribute("id")
    String id) {
        if (id == null) return false;
        return hazelcast.getMap(getDao().getMapName()).removeEntryListener(id);
    }

    protected abstract Dao<K, V> getDao();
}

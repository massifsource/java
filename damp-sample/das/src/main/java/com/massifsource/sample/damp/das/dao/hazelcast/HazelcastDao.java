/**
 * MassifSource
 * @year 2015
 * @author nsarychev
 */
package com.massifsource.sample.damp.das.dao.hazelcast;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;

import com.hazelcast.query.Predicate;
import com.massifsource.sample.damp.das.dao.Dao;
import com.massifsource.sample.damp.das.dao.exception.HazelcastDataAccessException;
import com.massifsource.sample.damp.das.domain.CacheableEntity;
import com.massifsource.sample.damp.das.hazelcast.HazelcastCache;
@SuppressWarnings({"rawtypes", "unchecked"})
public abstract class HazelcastDao<K extends Serializable, V extends CacheableEntity<K>> implements
    Dao<K, V> {

    @Autowired
    protected HazelcastCache hazelcastCache;

    protected boolean autoGenerateId = false;

    protected boolean synchronizeCache = true;

    protected String mapName;
    
    private Class keyClazz;
    
    public HazelcastDao() {
        // Need to determine the class of the K type parameter. The [0] array index relies on 
        // the order that the generic type parameters were declared.
        this.keyClazz 
            = (Class) 
            ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments() [0];
    }

    @PostConstruct
    public void setup() {
        hazelcastCache.setMapName(mapName);
        hazelcastCache.setSynchronizeCache(synchronizeCache);
        hazelcastCache.setup();
    }
    
    public String getMapName() {
        return this.mapName;
    }

    public void create(V dto) throws DataAccessException {
        if (dto == null) {
            return;
        }
        create(dto, true);
    }

    protected void create(V entity, boolean generateId) throws DataAccessException {
        if (generateId) {
            generateIdIfNotExists(entity);
        }
        hazelcastCache.put(entity.getKey(), entity);
    }

    public void create(List<V> dtos) throws DataAccessException {
        if (dtos == null) {
            return;
        }
        create(dtos, true);
    }

    protected void create(List<? extends V> entities, boolean generateIds)
        throws DataAccessException {
        if (entities.isEmpty()) {
            return;
        }
        Map<K, V> map = new HashMap<K, V>();
        for (V entity : entities) {
            if (generateIds) {
                generateIdIfNotExists(entity);
            }
            map.put(entity.getKey(), entity);
        }
        if (!map.isEmpty()) {
            hazelcastCache.putAll(map);
        }
    }

    public List<V> read(List<K> keys) throws DataAccessException {
        if (keys == null) {
            return null;
        }
        Set<K> keySet = new HashSet<K>(keys);
        return new ArrayList<V>(hazelcastCache.getAll(keySet).values());
    }

    public List<V> read() throws DataAccessException {
        return new ArrayList<V>(hazelcastCache.values());
    }

    public void update(List<? extends V> dtos) throws DataAccessException {
        if (dtos == null) {
            return;
        }
        create(dtos, false);
    }

    public void delete(K key) throws DataAccessException {
        if (key == null) {
            return;
        }
        hazelcastCache.remove(key);
    }

    public void delete(V entity) throws DataAccessException {
        if (entity == null) {
            return;
        }
        delete(entity.getKey());
    }

    public void delete(List<K> keys) throws DataAccessException {
        if (keys == null) {
            return;
        }
        hazelcastCache.removeAll(keys);

    }

    public V read(K key) throws DataAccessException {
        if (key == null) {
            return null;
        }
        return (V) hazelcastCache.get(key);
    }

    public void update(V dto) throws DataAccessException {
        if (dto == null) {
            return;
        }
        create(dto, false);
    }

    public Integer count() throws DataAccessException {
        return hazelcastCache.size();
    }

    public void deleteAll() {
        /**
         * cache.clear() doesn't replicate over WAN
         */
        hazelcastCache.removeAll(hazelcastCache.keySet());
    }

    protected void deleteList(List<V> list) {
        if (list == null) {
            return;
        }
        for (V value : list) {
            hazelcastCache.remove(value.getKey());
        }
    }

    public void setCache(HazelcastCache<K, V> cache) {
        this.hazelcastCache = cache;
    }

    protected void generateIdIfNotExists(V entity) throws DataAccessException {
        if (entity == null) {
            return;
        }
        if (entity.getKey() == null && autoGenerateId) {
            if (!Long.class.equals(keyClazz)) {
                throw new HazelcastDataAccessException("Impossible to auto-generate new key for class "
                    + keyClazz.getClass().toString());
            }
            else {
                entity.setKey((K) (Long) hazelcastCache.getIdGenerator().newId());
            }
        }
    }

    public abstract void setMapName(String mapName);

    public void setSynchronizeCache(boolean synchronizeCache) {
        this.synchronizeCache = synchronizeCache;
    }

    public void setAutoGenerateId(boolean autoGenerateId) {
        this.autoGenerateId = autoGenerateId;
    }

    public void replace(V currentEntity, V newEntity) throws DataAccessException {
        if (newEntity == null) {
            return;
        }
        if (currentEntity != null) {
            delete(currentEntity);
        }
        create(newEntity);

    }

    public V getOne(Predicate<K, V> query) {
        if (query == null) {
            return null;
        }
        Collection<V> result = hazelcastCache.values(query);
        if (result != null && !result.isEmpty()) {
            return result.iterator().next();
        }
        return null;
    }

    public List<V> getList(Predicate<K, V> query) {
        if (query == null) {
            return Collections.EMPTY_LIST;
        }
        Collection<V> result = hazelcastCache.values(query);
        if (result != null && !result.isEmpty()) {
            return new ArrayList<V>(result);
        }
        return Collections.EMPTY_LIST;
    }

}

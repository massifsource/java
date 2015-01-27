/**
 * MassifSource
 * @year 2015
 * @author nsarychev
 */
package com.massifsource.sample.damp.das.hazelcast;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.hazelcast.core.IdGenerator;
import com.hazelcast.query.Predicate;
import com.hazelcast.transaction.TransactionContext;

public interface HazelcastCache <K extends Serializable,V extends Serializable>{
    
    public void removeAll(Collection<K> keys);
    
    public Map<K, V> getAll(Set<K> keys);

    public Set<Entry<K, V>> entrySet();

    public Collection<V> values(Predicate<K, V> query);
    
    public Collection<V> values();

    public Set<K> keySet();

    public void clear();

    public int size();

    public boolean isEmpty();

    public boolean containsKey(K key);

    public boolean containsValue(V value);

    public V get(K key);

    public V put(K key, V value);

    public V remove(K key);

    public void putAll(Map<? extends K, ? extends V> m);

    public Collection<V> get(Predicate<K, V> query);

    public V getOne(Predicate<K, V> query);

    public List<V> getList(Predicate<K, V> query);
    
    public void setup();
    
    public IdGenerator getIdGenerator();

    public TransactionContext getTransactionContext();
    
    public void setMapName(String mapName);
    
    public void setSynchronizeCache(boolean synchronizeCache);
    
}

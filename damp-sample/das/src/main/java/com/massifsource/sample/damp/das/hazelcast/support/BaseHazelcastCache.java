/**
 * MassifSource
 * @year 2015
 * @author nsarychev
 */
package com.massifsource.sample.damp.das.hazelcast.support;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import com.hazelcast.core.IdGenerator;
import com.hazelcast.query.Predicate;
import com.hazelcast.transaction.TransactionContext;
import com.hazelcast.transaction.TransactionOptions;
import com.hazelcast.transaction.TransactionOptions.TransactionType;
import com.massifsource.sample.damp.das.hazelcast.HazelcastCache;

public abstract class BaseHazelcastCache<K extends Serializable, V extends Serializable>
		implements HazelcastCache<K, V> {

	@Autowired
	protected HazelcastInstanceProvider instanceProvider;
	protected boolean transactional = true;
	protected TransactionType transactionType = TransactionType.LOCAL;
	protected TransactionContext transactionContext;
	protected String mapName;
	protected IdGenerator idGenerator;

	public void setup() {
		setIdGenerator(instanceProvider.getInstance().getIdGenerator(this.mapName));
		TransactionOptions options = new TransactionOptions()
				.setTransactionType(transactionType);
		setTransactionContext(instanceProvider.getInstance().newTransactionContext(options));
	}

	public HazelcastInstance getInstance() {
		return instanceProvider.getInstance();
	}

	public void setInstance(HazelcastInstance instance) {
		this.instanceProvider.setInstance(instance);
	}

	public String getMapName() {
		return mapName;
	}

	public void setMapName(String mapName) {
		this.mapName = mapName;
	}

	public int size() {
		return getMap().size();
	}
	
	public boolean isEmpty() {
		return getMap().isEmpty();
	}

	public boolean containsKey(K key) {
		return getMap().containsKey(key);
	}
	
	public boolean containsValue(V value) {
		return getMap().containsValue(value);
	}
	
	public V get(K key) {
		return getMap().get(key);
	}
	
	public V put(K key, V value) {
		if (key == null)
			return null;
		return getMap().put(key, value);
	}
	
	public V remove(K key) {
		if (key == null)
			return null;
		return getMap().remove(key);
	}

	public void putAll(Map<? extends K, ? extends V> m) {
		if (m != null)
			getMap().putAll(m);
	}
	
	public void clear() {
		getMap().clear();
	}
	
	public Set<K> keySet() {
		return getMap().keySet();
	}
	
	public Collection<V> values() {
		return getMap().values();
	}
	
	public Collection<V> values(Predicate<K, V> query) {
		return getMap().values(query);
	}

	public Set<java.util.Map.Entry<K, V>> entrySet() {
		return getMap().entrySet();
	}

	public void removeAll(Collection<K> keys) {
		if (keys == null)
			return;
		for (K key : keys) {
			getMap().remove(key);
		}
	}

	
	public Map<K, V> getAll(Set<K> keys) {
		if (keys == null)
			return null;
		return getMap().getAll(keys);
	}

	
	public Collection<V> get(Predicate<K, V> query) {
		if (query == null)
			return null;
		return getMap().values(query);
	}

	
	public V getOne(Predicate<K, V> query) {
		if (query == null)
			return null;
		Collection<V> result = getMap().values(query);
		if (result != null && !result.isEmpty()) {
			return result.iterator().next();
		}
		return null;
	}

	public List<V> getList(Predicate<K, V> query) {
		if (query == null)
			return null;
		Collection<V> result = getMap().values(query);
		if (result != null && !result.isEmpty()) {
			return new ArrayList<V>(result);
		}
		return null;
	}

	public IdGenerator getIdGenerator() {
		return idGenerator;
	}

	public void setIdGenerator(IdGenerator idGenerator) {
		this.idGenerator = idGenerator;
	}

	public boolean isTransactional() {
		return transactional;
	}

	public void setTransactional(boolean transactional) {
		this.transactional = transactional;
	}

	public TransactionType getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(TransactionType transactionType) {
		this.transactionType = transactionType;
	}

	public TransactionContext getTransactionContext() {
		return transactionContext;
	}

	public void setTransactionContext(TransactionContext transactionContext) {
		this.transactionContext = transactionContext;
	}

	protected IMap<K, V> getMap() {
		return instanceProvider.getInstance().getMap(mapName);
	}
}
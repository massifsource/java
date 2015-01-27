/**
 * MassifSource
 * @year 2015
 * @author nsarychev
 */
package com.massifsource.sample.damp.das.dao;

import java.io.Serializable;
import java.util.List;

import org.springframework.dao.DataAccessException;

import com.massifsource.sample.damp.das.domain.CacheableEntity;

public interface Dao <K  extends Serializable, V  extends CacheableEntity<K>> {
    
    public Integer count() throws DataAccessException;

    public void create(V entity) throws DataAccessException;
    
	public void create(List<V> entities) throws DataAccessException;

	public List<V> read(List<K> keys) throws DataAccessException;

	public List<V> read() throws DataAccessException;
	
	public V read(K key) throws DataAccessException;

	public void update(List<? extends V> entities) throws DataAccessException;
	
	public void update(V entity) throws DataAccessException;
	
	public void replace(V currentEntity, V newEntity) throws DataAccessException;

	public void delete(K key) throws DataAccessException;

	public void delete(List<K> keys) throws DataAccessException;
	
	public void delete(V entity) throws DataAccessException;
	
	public void deleteAll() throws DataAccessException;
	
	public String getMapName();
	
}
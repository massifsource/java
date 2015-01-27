/**
 * MassifSource
 * @year 2015
 * @author nsarychev
 */
package com.massifsource.sample.damp.das.dao.hazelcast.test;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;

import com.massifsource.sample.damp.das.domain.CacheableEntity;

public abstract class TestDataProvider<K extends Serializable, V  extends CacheableEntity<K>> {
    
    public abstract List<V> getData ();
    
    @PostConstruct
    public void populate () {
        populate(getData());
    }
    
    public abstract void populate (List<V> data);
}

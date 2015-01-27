/**
 * MassifSource
 * @year 2015
 * @author nsarychev
 */
package com.massifsource.sample.damp.das.domain;

import java.io.Serializable;

public abstract class CacheableEntity<K extends Serializable> implements Serializable {
  
    private static final long serialVersionUID = 1L;
    
    public abstract K getKey();
    
    public abstract void setKey(K key);
    
}

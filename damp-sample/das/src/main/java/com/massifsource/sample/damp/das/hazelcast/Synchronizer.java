/**
 * MassifSource
 * @year 2015
 * @author nsarychev
 */
package com.massifsource.sample.damp.das.hazelcast;

import com.hazelcast.core.IMap;

public interface Synchronizer {

    public void synchronizeCache(@SuppressWarnings("rawtypes") IMap cache);

}

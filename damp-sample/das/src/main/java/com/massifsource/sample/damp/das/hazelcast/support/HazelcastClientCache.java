/**
 * MassifSource
 * @year 2015
 * @author nsarychev
 */
package com.massifsource.sample.damp.das.hazelcast.support;

import java.io.Serializable;

import com.massifsource.sample.damp.das.hazelcast.HazelcastCache;

public class HazelcastClientCache<K extends Serializable, V extends Serializable>
		extends BaseHazelcastCache<K, V> implements HazelcastCache<K, V> {

	public void setSynchronizeCache(boolean synchronizeCache) {
		// DO NOTHING no sync if in client mode
	}

}

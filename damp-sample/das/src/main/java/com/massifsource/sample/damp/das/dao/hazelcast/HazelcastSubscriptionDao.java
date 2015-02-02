/**
 * MassifSource
 * @year 2015
 * @author nsarychev
 */
package com.massifsource.sample.damp.das.dao.hazelcast;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.massifsource.sample.damp.das.dao.SubscriptionDao;
import com.massifsource.sample.damp.das.domain.Subscription;

@Component(value = "subscriptionDao")
public class HazelcastSubscriptionDao extends HazelcastDao<Long, Subscription> implements
    SubscriptionDao {

    public HazelcastSubscriptionDao() {
        super();
        autoGenerateId = true;
    }
    
    @Autowired
    public void setMapName(@Value("${map.name.subscription}")
    String mapName) {
        super.mapName = mapName;
    }

}
/**
 * MassifSource
 * @year 2015
 * @author nsarychev
 */
package com.massifsource.sample.damp.das.dao.hazelcast;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.hazelcast.query.EntryObject;
import com.hazelcast.query.Predicate;
import com.hazelcast.query.PredicateBuilder;
import com.hazelcast.query.Predicates;
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

    @SuppressWarnings("unchecked")
    public List<Subscription> readByPsapNameAndEvent(String psapName, String event) {
        EntryObject e = new PredicateBuilder().getEntryObject();
        Predicate<Long, Subscription> query = e.get("psapName").equal(psapName).and(e.get("event").equal(event));
        return getList(query);
    }

    @SuppressWarnings("unchecked")
    public List<Subscription> readByParentSubscriptionId(long parentSubscriptionId) {
        return new ArrayList<Subscription>(hazelcastCache.values(new Predicates.EqualPredicate("parentSubscriptionId", 
            parentSubscriptionId)));
    }

}
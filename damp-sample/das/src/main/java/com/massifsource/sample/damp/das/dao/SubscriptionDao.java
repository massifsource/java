/**
 * MassifSource
 * @year 2015
 * @author nsarychev
 */
package com.massifsource.sample.damp.das.dao;

import java.util.List;

import com.massifsource.sample.damp.das.domain.Subscription;

public interface SubscriptionDao extends Dao<Long, Subscription> {

    List<Subscription> readByPsapNameAndEvent(String psapName, String event);

    List<Subscription> readByParentSubscriptionId(long parentSubscriptionId);
   
}
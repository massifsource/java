/**
 * MassifSource
 * @year 2015
 * @author nsarychev
 */
package com.massifsource.sample.damp.das.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.massifsource.sample.damp.das.dao.Dao;
import com.massifsource.sample.damp.das.dao.SubscriptionDao;
import com.massifsource.sample.damp.das.domain.Subscription;

@RestController
@RequestMapping("/maps/${map.name.subscription}")
public class SubscriptionController extends
    BaseCrudController<Long, Subscription> {

    @Autowired
    private SubscriptionDao dao;

    @Override
    protected Dao<Long, Subscription> getDao() {
        return this.dao;
    }

}

/**
 * MassifSource
 * @year 2015
 * @author nsarychev
 */
package com.massifsource.sample.damp.das.dao.hazelcast;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.massifsource.sample.damp.das.dao.ProductStateDao;
import com.massifsource.sample.damp.das.domain.ProductState;

@Component(value="productStateDao")
public class HazelcastProductStateDao extends HazelcastDao<Long, ProductState>
    implements ProductStateDao {

    @Autowired
    public void setMapName(@Value("${map.name.product.state}")
    String mapName) {
        super.mapName = mapName;
    }

}
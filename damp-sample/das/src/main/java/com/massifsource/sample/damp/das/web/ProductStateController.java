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
import com.massifsource.sample.damp.das.dao.ProductStateDao;
import com.massifsource.sample.damp.das.domain.ProductState;

@RestController
@RequestMapping("/maps/${map.name.product.state}")
public class ProductStateController extends BaseCrudController<Long, ProductState>{

    @Autowired
    private ProductStateDao dao;
  
    @Override
    protected Dao<Long, ProductState> getDao() {
        return this.dao;
    }
 
}

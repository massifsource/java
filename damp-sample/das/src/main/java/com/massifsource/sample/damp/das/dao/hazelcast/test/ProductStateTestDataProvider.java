/**
 * MassifSource
 * @year 2015
 * @author nsarychev
 */
package com.massifsource.sample.damp.das.dao.hazelcast.test;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.massifsource.sample.damp.das.dao.ProductStateDao;
import com.massifsource.sample.damp.das.domain.ProductState;
import com.massifsource.sample.damp.das.domain.ProductStatus;

public class ProductStateTestDataProvider extends TestDataProvider<Long, ProductState>{
    
    @Autowired
    private ProductStateDao dao;
 
    public void populate(List<ProductState> data) {
        dao.create(data);
    }
    
    public List<ProductState> getData () {
        List<ProductState> list = new ArrayList<ProductState>();
        list.add(new ProductState(6L, ProductStatus.AVAILABLE));
        list.add(new ProductState(7L, ProductStatus.AVAILABLE));
        list.add(new ProductState(8L, ProductStatus.AVAILABLE));
        list.add(new ProductState(10L, ProductStatus.AVAILABLE));
        list.add(new ProductState(12L, ProductStatus.AVAILABLE));
        list.add(new ProductState(13L, ProductStatus.AVAILABLE));
        list.add(new ProductState(14L, ProductStatus.AVAILABLE));
        list.add(new ProductState(18L, ProductStatus.AVAILABLE));
        list.add(new ProductState(19L, ProductStatus.AVAILABLE));
        list.add(new ProductState(20L, ProductStatus.AVAILABLE));
        list.add(new ProductState(21L, ProductStatus.AVAILABLE));
        list.add(new ProductState(22L, ProductStatus.AVAILABLE));
        list.add(new ProductState(23L, ProductStatus.AVAILABLE));
        list.add(new ProductState(24L, ProductStatus.AVAILABLE));
        list.add(new ProductState(25L, ProductStatus.AVAILABLE));
        list.add(new ProductState(26L, ProductStatus.AVAILABLE));
        list.add(new ProductState(27L, ProductStatus.AVAILABLE));
        list.add(new ProductState(28L, ProductStatus.AVAILABLE));
        list.add(new ProductState(29L, ProductStatus.AVAILABLE));
        list.add(new ProductState(30L, ProductStatus.AVAILABLE));
        list.add(new ProductState(31L, ProductStatus.AVAILABLE));
        list.add(new ProductState(32L, ProductStatus.AVAILABLE));
        list.add(new ProductState(33L, ProductStatus.AVAILABLE));
        list.add(new ProductState(34L, ProductStatus.AVAILABLE));
        list.add(new ProductState(35L, ProductStatus.AVAILABLE));
        list.add(new ProductState(36L, ProductStatus.AVAILABLE));
        list.add(new ProductState(37L, ProductStatus.AVAILABLE));
        list.add(new ProductState(38L, ProductStatus.AVAILABLE));
        list.add(new ProductState(39L, ProductStatus.AVAILABLE));
        list.add(new ProductState(40L, ProductStatus.AVAILABLE));
        list.add(new ProductState(41L, ProductStatus.AVAILABLE));
        list.add(new ProductState(100L, ProductStatus.AVAILABLE));
        list.add(new ProductState(101L, ProductStatus.AVAILABLE));
        list.add(new ProductState(102L, ProductStatus.AVAILABLE));
        list.add(new ProductState(103L, ProductStatus.AVAILABLE));
        list.add(new ProductState(104L, ProductStatus.AVAILABLE));
        return list;
    }

}

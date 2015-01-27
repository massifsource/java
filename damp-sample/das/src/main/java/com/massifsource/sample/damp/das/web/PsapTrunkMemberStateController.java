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
import com.massifsource.sample.damp.das.dao.PsapTrunkMemberStateDao;
import com.massifsource.sample.damp.das.domain.PsapTrunkMemberState;

@RestController
@RequestMapping("/maps/${map.name.psap.trunk.member.state}")
public class PsapTrunkMemberStateController extends BaseCrudController<Long, PsapTrunkMemberState>{

    @Autowired
    private PsapTrunkMemberStateDao dao;
  
    @Override
    protected Dao<Long, PsapTrunkMemberState> getDao() {
        return this.dao;
    }
 
}

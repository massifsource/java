/**
 * MassifSource
 * @year 2015
 * @author nsarychev
 */
package com.massifsource.sample.damp.das.dao.hazelcast.test;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.massifsource.sample.damp.das.dao.PsapTrunkMemberStateDao;
import com.massifsource.sample.damp.das.domain.PsapTrunkMemberState;
import com.massifsource.sample.damp.das.domain.PsapTrunkMemberStatus;

public class PsapTrunkMemberStateTestDataProvider extends TestDataProvider<Long, PsapTrunkMemberState>{
    
    @Autowired
    private PsapTrunkMemberStateDao dao;
 
    public void populate(List<PsapTrunkMemberState> data) {
        dao.create(data);
    }
    
    public List<PsapTrunkMemberState> getData () {
        List<PsapTrunkMemberState> list = new ArrayList<PsapTrunkMemberState>();
        list.add(new PsapTrunkMemberState(6L, PsapTrunkMemberStatus.InService, false, false, "cck"));
        list.add(new PsapTrunkMemberState(7L, PsapTrunkMemberStatus.InService, false, false, "cck"));
        list.add(new PsapTrunkMemberState(8L, PsapTrunkMemberStatus.InService, false, false, "cck"));
        list.add(new PsapTrunkMemberState(10L, PsapTrunkMemberStatus.InService, false, false, "cck"));
        list.add(new PsapTrunkMemberState(12L, PsapTrunkMemberStatus.InService, false, false, "cck"));
        list.add(new PsapTrunkMemberState(13L, PsapTrunkMemberStatus.InService, false, false, "cck"));
        list.add(new PsapTrunkMemberState(14L, PsapTrunkMemberStatus.InService, true, false, "cck"));
        list.add(new PsapTrunkMemberState(18L, PsapTrunkMemberStatus.InService, false, false, "cck"));
        list.add(new PsapTrunkMemberState(19L, PsapTrunkMemberStatus.InService, false, false, "cck"));
        list.add(new PsapTrunkMemberState(20L, PsapTrunkMemberStatus.InService, false, false, "cck"));
        list.add(new PsapTrunkMemberState(21L, PsapTrunkMemberStatus.InService, false, false, "cck"));
        list.add(new PsapTrunkMemberState(22L, PsapTrunkMemberStatus.InService, false, false, "cck"));
        list.add(new PsapTrunkMemberState(23L, PsapTrunkMemberStatus.InService, false, false, "cck"));
        list.add(new PsapTrunkMemberState(24L, PsapTrunkMemberStatus.InService, false, false, "cck"));
        list.add(new PsapTrunkMemberState(25L, PsapTrunkMemberStatus.InService, false, false, "cck"));
        list.add(new PsapTrunkMemberState(26L, PsapTrunkMemberStatus.InService, false, false, "cck"));
        list.add(new PsapTrunkMemberState(27L, PsapTrunkMemberStatus.InService, false, false, "cck"));
        list.add(new PsapTrunkMemberState(28L, PsapTrunkMemberStatus.InService, false, false, "cck"));
        list.add(new PsapTrunkMemberState(29L, PsapTrunkMemberStatus.InService, false, false, "cck"));
        list.add(new PsapTrunkMemberState(30L, PsapTrunkMemberStatus.InService, false, false, "cck"));
        list.add(new PsapTrunkMemberState(31L, PsapTrunkMemberStatus.InService, true, false, "cck"));
        list.add(new PsapTrunkMemberState(32L, PsapTrunkMemberStatus.InService, false, false, "cck"));
        list.add(new PsapTrunkMemberState(33L, PsapTrunkMemberStatus.InService, false, false, "cck"));
        list.add(new PsapTrunkMemberState(34L, PsapTrunkMemberStatus.InService, false, false, "cck"));
        list.add(new PsapTrunkMemberState(35L, PsapTrunkMemberStatus.InService, false, false, "cck"));
        list.add(new PsapTrunkMemberState(36L, PsapTrunkMemberStatus.InService, false, false, "cck"));
        list.add(new PsapTrunkMemberState(37L, PsapTrunkMemberStatus.InService, false, false, "cck"));
        list.add(new PsapTrunkMemberState(38L, PsapTrunkMemberStatus.InService, true, false, "cck"));
        list.add(new PsapTrunkMemberState(39L, PsapTrunkMemberStatus.InService, true, false, "cck"));
        list.add(new PsapTrunkMemberState(40L, PsapTrunkMemberStatus.InService, false, false, "cck"));
        list.add(new PsapTrunkMemberState(41L, PsapTrunkMemberStatus.InService, false, false, "cck"));
        list.add(new PsapTrunkMemberState(100L, PsapTrunkMemberStatus.InService, false, false, "cck"));
        list.add(new PsapTrunkMemberState(101L, PsapTrunkMemberStatus.InService, false, false, "cck"));
        list.add(new PsapTrunkMemberState(102L, PsapTrunkMemberStatus.InService, false, false, "cck"));
        list.add(new PsapTrunkMemberState(103L, PsapTrunkMemberStatus.InService, false, false, "cck"));
        list.add(new PsapTrunkMemberState(104L, PsapTrunkMemberStatus.InService, false, false, "cck"));
        return list;
    }

}

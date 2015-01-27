/**
 * MassifSource
 * @year 2015
 * @author nsarychev
 */
package com.massifsource.sample.damp.das.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.massifsource.sample.damp.das.domain.PsapTrunkMemberState;

public interface PsapTrunkMemberStateDao extends Dao<Long, PsapTrunkMemberState> {

    public PsapTrunkMemberState setPtmIdle(Long psapTrunkMemberId, String cck) throws DataAccessException;

    public void setPtmBusy(Long psapTrunkMemberId, String cck) throws DataAccessException;

    public void setForcePtmBusy(Long psapTrunkMemberId, boolean busy, boolean forceBusy)
        throws DataAccessException;

    public List<PsapTrunkMemberState> readBusyInServicePtms() throws DataAccessException;

    public List<PsapTrunkMemberState> readUnavailable() throws DataAccessException;
    
    public List<PsapTrunkMemberState> readAllPtmsBusyForLongerThanGivenSeconds(int seconds) throws DataAccessException;
    
}
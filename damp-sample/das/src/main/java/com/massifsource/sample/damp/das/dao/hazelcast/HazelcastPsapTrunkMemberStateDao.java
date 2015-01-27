/**
 * MassifSource
 * @year 2015
 * @author nsarychev
 */
package com.massifsource.sample.damp.das.dao.hazelcast;


import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

import com.hazelcast.query.EntryObject;
import com.hazelcast.query.Predicate;
import com.hazelcast.query.PredicateBuilder;
import com.hazelcast.query.Predicates;
import com.massifsource.sample.damp.das.dao.PsapTrunkMemberStateDao;
import com.massifsource.sample.damp.das.domain.PsapTrunkMemberState;
import com.massifsource.sample.damp.das.domain.PsapTrunkMemberStatus;

@Component(value="psapTrunkMemberStateDao")
public class HazelcastPsapTrunkMemberStateDao extends HazelcastDao<Long, PsapTrunkMemberState>
    implements PsapTrunkMemberStateDao {

    @Autowired
    public void setMapName(@Value("${map.name.psap.trunk.member.state}")
    String mapName) {
        super.mapName = mapName;
    }

    public PsapTrunkMemberState setPtmIdle(Long psapTrunkMemberId, String cck) throws DataAccessException {
        PsapTrunkMemberState ptmState = read(psapTrunkMemberId);
        if (ptmState != null && ptmState.isBusy()) {
            ptmState.setBusy(false);
            ptmState.setForceBusy(false);
            ptmState.setCck(cck);
            update(ptmState);
        } 
        return ptmState;
        
    }

    public void setPtmBusy(Long psapTrunkMemberId, String cck) throws DataAccessException {
        PsapTrunkMemberState ptmState = read(psapTrunkMemberId);
        if (ptmState != null) {
            ptmState.setBusy(true);
            ptmState.setCck(cck);
            update(ptmState);
        }
    }
    
    public void setForcePtmBusy(Long psapTrunkMemberId, boolean busy, boolean forceBusy)
        throws DataAccessException {
        PsapTrunkMemberState ptmState = read(psapTrunkMemberId);
        if (ptmState != null) {
            ptmState.setForceBusy(forceBusy);
            ptmState.setBusy(busy);
            update(ptmState);
        }

    }

    @SuppressWarnings("unchecked")
    public List<PsapTrunkMemberState> readBusyInServicePtms() throws DataAccessException {
        EntryObject e = new PredicateBuilder().getEntryObject();
        Predicate<Long, PsapTrunkMemberState> query = e.get("busy").equal(true).and(e.get("psapTrunkMemberStatus").equal(PsapTrunkMemberStatus.InService));
        return getList(query);
    }

    @SuppressWarnings("unchecked")
    public List<PsapTrunkMemberState> readUnavailable() throws DataAccessException {
        return new ArrayList<PsapTrunkMemberState>(hazelcastCache.values(new Predicates.NotEqualPredicate("psapTrunkMemberStatus", 
            PsapTrunkMemberStatus.InService)));
    }

	@SuppressWarnings("unchecked")
	public List<PsapTrunkMemberState> readAllPtmsBusyForLongerThanGivenSeconds(
			int seconds) throws DataAccessException {
		Timestamp criteria = new Timestamp(System.currentTimeMillis() - (seconds * 1000));
		EntryObject e = new PredicateBuilder().getEntryObject();
		Predicate<Long, PsapTrunkMemberState> query = (e.get("busy").equal(true).or(e.get("forceBusy").equal(true))).and(e.get("leastRecentUsedTime").lessThan(criteria));
		return getList(query);
	}

}
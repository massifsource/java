/**
 * MassifSource
 * @year 2015
 * @author nsarychev
 */
package com.massifsource.sample.damp.das.domain;

import java.sql.Timestamp;

public class PsapTrunkMemberState extends CacheableEntity<Long> {

    private static final long serialVersionUID = 1L;

    private long psapTrunkMemberId;

    private boolean busy;

    private boolean forceBusy;

    private PsapTrunkMemberStatus psapTrunkMemberStatus;
    
    private String cck;

    private Timestamp leastRecentUsedTime;
    
    public PsapTrunkMemberState() {
    }

    public PsapTrunkMemberState(Long psapTrunkMemberId,
        PsapTrunkMemberStatus psapTrunkMemberStatus, boolean busy, boolean forceBusy, String cck) {
        this(psapTrunkMemberId, psapTrunkMemberStatus, busy, forceBusy, cck, new Timestamp(System.currentTimeMillis()));
    }

    public PsapTrunkMemberState(Long psapTrunkMemberId,
        PsapTrunkMemberStatus psapTrunkMemberStatus, boolean busy, boolean forceBusy, String cck,
        Timestamp leastRecentUsedTime) {
        this.psapTrunkMemberId = psapTrunkMemberId;
        this.psapTrunkMemberStatus = psapTrunkMemberStatus;
        this.cck = cck;
        this.busy = busy;
        this.forceBusy = forceBusy;
        this.leastRecentUsedTime = leastRecentUsedTime;
    }

    public PsapTrunkMemberState(Long psapTrunkMemberId,
        PsapTrunkMemberStatus psapTrunkMemberStatus) {
        this(psapTrunkMemberId, psapTrunkMemberStatus, false, false, null);
    }

    public boolean isBusy() {
        return this.busy;
    }

    public void setBusy(boolean busy) {
        this.busy = busy;
    }

    public boolean isForceBusy() {
        return this.forceBusy;
    }

    public void setForceBusy(boolean forceBusy) {
        this.forceBusy = forceBusy;
    }

    public boolean equals(Object object) {
        if (!(object instanceof PsapTrunkMemberState)) {
            return false;
        }
        PsapTrunkMemberState rhs = (PsapTrunkMemberState) object;
        return com.google.common.base.Objects.equal(this.psapTrunkMemberId,
            rhs.psapTrunkMemberId)
            && com.google.common.base.Objects.equal(this.psapTrunkMemberStatus,
                rhs.psapTrunkMemberStatus)
            && com.google.common.base.Objects.equal(this.busy, rhs.busy)
            && com.google.common.base.Objects.equal(this.forceBusy, rhs.forceBusy)
            && com.google.common.base.Objects.equal(this.cck, rhs.cck)
            && com.google.common.base.Objects.equal(this.leastRecentUsedTime,
                rhs.leastRecentUsedTime);
    }

    public int hashCode() {
        return com.google.common.base.Objects.hashCode(this.psapTrunkMemberId,
            this.psapTrunkMemberStatus, this.busy, this.forceBusy, this.cck, this.leastRecentUsedTime);
    }

    public String toString() {
        return com.google.common.base.Objects.toStringHelper(this)
            .addValue(this.getPsapTrunkMemberId()).addValue(this.getPsapTrunkMemberStatus())
            .addValue(this.isBusy()).addValue(this.getCck()).addValue(this.getLeastRecentUsedTime()).toString();
    }

    public PsapTrunkMemberStatus getPsapTrunkMemberStatus() {
        return psapTrunkMemberStatus;
    }

    public void setPsapTrunkMemberStatus(PsapTrunkMemberStatus psapTrunkMemberStatus) {
        this.psapTrunkMemberStatus = psapTrunkMemberStatus;
    }

    @Override
    public Long getKey() {
        return getPsapTrunkMemberId();
    }

    @Override
    public void setKey(Long key) {
        setPsapTrunkMemberId(key);
    }

    public Timestamp getLeastRecentUsedTime() {
        return this.leastRecentUsedTime;
    }

    public void setLeastRecentUsedTime(Timestamp leastRecentUsedTime) {
        this.leastRecentUsedTime = leastRecentUsedTime;
    }

    public Long getPsapTrunkMemberId() {
        return psapTrunkMemberId;
    }

    public void setPsapTrunkMemberId(Long psapTrunkMemberId) {
        this.psapTrunkMemberId = psapTrunkMemberId;
    }

    public String getCck() {
        return cck;
    }

    public void setCck(String cck) {
        this.cck = cck;
    }

}

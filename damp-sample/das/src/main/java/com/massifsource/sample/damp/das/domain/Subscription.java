/**
 * MassifSource
 * @year 2015
 * @author nsarychev
 */
package com.massifsource.sample.damp.das.domain;

import java.sql.Timestamp;

public class Subscription extends CacheableEntity<Long> {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String psapName;

    private String contactUri;

    private String eventIdentifier;

    private String event;

    private Timestamp expirationTimestamp;

    private long siteId;

    private String sessionIdentifier;

    private String clusterSessionIdentifier;

    private Long parentSubscriptionId;

    public Subscription() {
    }

    public Subscription(Long id, String psapName, String contactUri, String eventIdentifier,
        String event, Timestamp expirationTimestamp, long siteId, String clusterSessionIdentifier,
        String sessionIdentifier, Long parentSubscriptionId) {
        this.id = id;
        this.psapName = psapName;
        this.contactUri = contactUri;
        this.eventIdentifier = eventIdentifier;
        this.event = event;
        this.expirationTimestamp = expirationTimestamp;
        this.siteId = siteId;
        this.sessionIdentifier = sessionIdentifier;
        this.clusterSessionIdentifier = clusterSessionIdentifier;
        this.parentSubscriptionId = parentSubscriptionId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPsapName() {
        return this.psapName;
    }

    public void setPsapName(String psapName) {
        if (psapName != null) {
            this.psapName = psapName.trim();
        }
        else {
            this.psapName = null;
        }
    }

    public String getContactUri() {
        return this.contactUri;
    }

    public void setContactUri(String contactUri) {
        if (contactUri != null) {
            this.contactUri = contactUri.trim();
        }
        else {
            this.contactUri = null;
        }
    }

    public String getEventIdentifier() {
        return this.eventIdentifier;
    }

    public void setEventIdentifier(String eventIdentifier) {
        if (eventIdentifier != null) {
            this.eventIdentifier = eventIdentifier.trim();
        }
        else {
            this.eventIdentifier = null;
        }
    }

    public String getEvent() {
        return this.event;
    }

    public void setEvent(String event) {
        if (event != null) {
            this.event = event.trim();
        }
        else {
            this.event = null;
        }
    }

    public Timestamp getExpirationTimestamp() {
        return this.expirationTimestamp;
    }

    public void setExpirationTimestamp(Timestamp expirationTimestamp) {
        this.expirationTimestamp = expirationTimestamp;
    }

    public long getSiteId() {
        return this.siteId;
    }

    public void setSiteId(long siteId) {
        this.siteId = siteId;
    }

    public String getSessionIdentifier() {
        return this.sessionIdentifier;
    }

    public void setSessionIdentifier(String sessionIdentifier) {
        if (sessionIdentifier != null) {
            this.sessionIdentifier = sessionIdentifier.trim();
        }
        else {
            this.sessionIdentifier = null;
        }
    }

    public String getClusterSessionIdentifier() {
        return this.clusterSessionIdentifier;
    }

    public void setClusterSessionIdentifier(String clusterSessionIdentifier) {
        if (clusterSessionIdentifier != null) {
            this.clusterSessionIdentifier = clusterSessionIdentifier.trim();
        }
        else {
            this.clusterSessionIdentifier = null;
        }
    }

    public Long getParentSubscriptionId() {
        return this.parentSubscriptionId;
    }

    public void setParentSubscriptionId(Long parentSubscriptionId) {
        this.parentSubscriptionId = parentSubscriptionId;
    }

    public boolean equals(Object object) {
        if (!(object instanceof Subscription)) {
            return false;
        }
        Subscription rhs = (Subscription) object;
        return com.google.common.base.Objects.equal(this.id, rhs.id)
            && com.google.common.base.Objects.equal(this.psapName, rhs.psapName)
            && com.google.common.base.Objects.equal(this.contactUri, rhs.contactUri)
            && com.google.common.base.Objects.equal(this.eventIdentifier, rhs.eventIdentifier)
            && com.google.common.base.Objects.equal(this.event, rhs.event)
            && com.google.common.base.Objects.equal(this.expirationTimestamp,
                rhs.expirationTimestamp)
            && com.google.common.base.Objects.equal(this.siteId, rhs.siteId)
            && com.google.common.base.Objects.equal(this.sessionIdentifier, rhs.sessionIdentifier)
            && com.google.common.base.Objects.equal(this.clusterSessionIdentifier,
                rhs.clusterSessionIdentifier)
            && com.google.common.base.Objects.equal(this.parentSubscriptionId,
                rhs.parentSubscriptionId);
    }

    public int hashCode() {
        return com.google.common.base.Objects.hashCode(this.id, this.psapName, this.contactUri,
            this.eventIdentifier, this.event, this.expirationTimestamp, this.siteId,
            this.sessionIdentifier, this.clusterSessionIdentifier, this.parentSubscriptionId);
    }

    public String toString() {
        return com.google.common.base.Objects.toStringHelper(this).addValue(this.getId())
            .addValue(this.getPsapName()).addValue(this.getContactUri())
            .addValue(this.getEventIdentifier()).addValue(this.getEvent())
            .addValue(this.getExpirationTimestamp()).addValue(this.getSiteId())
            .addValue(this.getSessionIdentifier()).addValue(this.getClusterSessionIdentifier())
            .addValue(this.getParentSubscriptionId()).toString();
    }

    @Override
    public Long getKey() {
        return getId();
    }

    @Override
    public void setKey(Long key) {
        setId(key);
    }
}

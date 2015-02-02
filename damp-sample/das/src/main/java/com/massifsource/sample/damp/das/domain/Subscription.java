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

	private String productName;

	private String eventIdentifier;

	private String event;

	private Timestamp expirationTimestamp;

	private Long parentSubscriptionId;

	public Subscription() {
	}

	public Subscription(Long id, String productName, 
			String eventIdentifier, String event,
			Timestamp expirationTimestamp,
			Long parentSubscriptionId) {
		this.id = id;
		this.productName = productName;
		this.eventIdentifier = eventIdentifier;
		this.event = event;
		this.expirationTimestamp = expirationTimestamp;
		this.parentSubscriptionId = parentSubscriptionId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getProductName() {
		return this.productName;
	}

	public void setProductName(String productName) {
		if (productName != null) {
			this.productName = productName.trim();
		} else {
			this.productName = null;
		}
	}

	public String getEventIdentifier() {
		return this.eventIdentifier;
	}

	public void setEventIdentifier(String eventIdentifier) {
		if (eventIdentifier != null) {
			this.eventIdentifier = eventIdentifier.trim();
		} else {
			this.eventIdentifier = null;
		}
	}

	public String getEvent() {
		return this.event;
	}

	public void setEvent(String event) {
		if (event != null) {
			this.event = event.trim();
		} else {
			this.event = null;
		}
	}

	public Timestamp getExpirationTimestamp() {
		return this.expirationTimestamp;
	}

	public void setExpirationTimestamp(Timestamp expirationTimestamp) {
		this.expirationTimestamp = expirationTimestamp;
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
				&& com.google.common.base.Objects.equal(this.productName,
						rhs.productName)
				&& com.google.common.base.Objects.equal(this.eventIdentifier,
						rhs.eventIdentifier)
				&& com.google.common.base.Objects.equal(this.event, rhs.event)
				&& com.google.common.base.Objects.equal(
						this.expirationTimestamp, rhs.expirationTimestamp)
				&& com.google.common.base.Objects.equal(
						this.parentSubscriptionId, rhs.parentSubscriptionId);
	}

	public int hashCode() {
		return com.google.common.base.Objects.hashCode(this.id,
				this.productName, this.eventIdentifier, this.event,
				this.expirationTimestamp, this.parentSubscriptionId);
	}

	public String toString() {
		return com.google.common.base.Objects.toStringHelper(this)
				.addValue(this.getId()).addValue(this.getProductName())
				.addValue(this.getEventIdentifier()).addValue(this.getEvent())
				.addValue(this.getExpirationTimestamp())
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

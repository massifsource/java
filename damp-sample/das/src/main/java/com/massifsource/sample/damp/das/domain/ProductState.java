/**
 * MassifSource
 * @year 2015
 * @author nsarychev
 */
package com.massifsource.sample.damp.das.domain;

import java.sql.Timestamp;

public class ProductState extends CacheableEntity<Long> {

    private static final long serialVersionUID = 1L;

    private long productId;

    private ProductStatus productStatus;

    private Timestamp leastRecentUsedTime;
    
    public ProductState() {
    }

    public ProductState(Long productId,
        ProductStatus productStatus) {
        this(productId, productStatus, new Timestamp(System.currentTimeMillis()));
    }

    public ProductState(Long productId,
        ProductStatus productStatus, Timestamp leastRecentUsedTime) {
        this.productId = productId;
        this.productStatus = productStatus;
        this.leastRecentUsedTime = leastRecentUsedTime;
    }

    public boolean equals(Object object) {
        if (!(object instanceof ProductState)) {
            return false;
        }
        ProductState rhs = (ProductState) object;
        return com.google.common.base.Objects.equal(this.productId,
            rhs.productId)
            && com.google.common.base.Objects.equal(this.productStatus,
                rhs.productStatus)
            && com.google.common.base.Objects.equal(this.leastRecentUsedTime,
                rhs.leastRecentUsedTime);
    }

    public int hashCode() {
        return com.google.common.base.Objects.hashCode(this.productId,
            this.productStatus, this.leastRecentUsedTime);
    }

    public String toString() {
        return com.google.common.base.Objects.toStringHelper(this)
            .addValue(this.getProductId()).addValue(this.getProductStatus())
            .addValue(this.getLeastRecentUsedTime()).toString();
    }

    public ProductStatus getProductStatus() {
        return productStatus;
    }

    public void setProductStatus(ProductStatus productStatus) {
        this.productStatus = productStatus;
    }

    @Override
    public Long getKey() {
        return getProductId();
    }

    @Override
    public void setKey(Long key) {
        setProductId(key);
    }

    public Timestamp getLeastRecentUsedTime() {
        return this.leastRecentUsedTime;
    }

    public void setLeastRecentUsedTime(Timestamp leastRecentUsedTime) {
        this.leastRecentUsedTime = leastRecentUsedTime;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

}

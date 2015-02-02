/**
 * MassifSource
 * @year 2015
 * @author nsarychev
 */
package com.massifsource.sample.damp.das.domain;


public enum ProductStatus implements CacheableEnum {

    AVAILABLE("Available"),
    OUT_OF_STOCK("Out Of Stock"),
    COMING_SOON("Coming Soon");

    private String displayName;
    
    private ProductStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
    
}

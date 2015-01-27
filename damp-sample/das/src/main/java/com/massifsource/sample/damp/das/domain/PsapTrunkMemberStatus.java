/**
 * MassifSource
 * @year 2015
 * @author nsarychev
 */
package com.massifsource.sample.damp.das.domain;


public enum PsapTrunkMemberStatus implements CacheableEnum {

    InService("In Service"),
    MOOS("MOOS"),
    AOOS("AOOS"),
    AOOS_RNA("AOOS-RNA"),
    AOOS_IN_TEST("AOOS-IN-TEST");

    private String displayName;
    
    private PsapTrunkMemberStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
    
}

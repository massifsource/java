/**
 * MassifSource
 * @year 2015
 * @author nsarychev
 */
package com.massifsource.sample.damp.common;

import java.io.Serializable;

public class ListenerInformation implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    public ListenerInformation() {
    }

    public ListenerInformation(String id) {
        super();
        this.setId(id);
    }

    public boolean equals(Object object) {
        if (!(object instanceof ListenerInformation)) {
            return false;
        }
        ListenerInformation rhs = (ListenerInformation) object;
        return com.google.common.base.Objects.equal(this.id, rhs.id);
    }

    public int hashCode() {
        return com.google.common.base.Objects.hashCode(this.id);
    }

    public String toString() {
        return com.google.common.base.Objects.toStringHelper(this).addValue(this.getId()).toString();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}

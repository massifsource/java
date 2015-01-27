/**
 * MassifSource
 * @year 2015
 * @author nsarychev
 */
package com.massifsource.sample.damp.common;

import java.io.Serializable;

public class DataReplicationRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    private String mapName;

    private String content;

    private Operation operation;

    public DataReplicationRequest() {
    }

    public DataReplicationRequest(String mapName, String content, Operation operation) {
        super();
        this.mapName = mapName;
        this.content = content;
        this.operation = operation;
    }

    public String getMapName() {
        return mapName;
    }

    public void setMapName(String mapName) {
        this.mapName = mapName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Operation getOperation() {
        return operation;
    }

    public void setOperation(Operation operation) {
        this.operation = operation;
    }

    public boolean equals(Object object) {
        if (!(object instanceof DataReplicationRequest)) {
            return false;
        }
        DataReplicationRequest rhs = (DataReplicationRequest) object;
        return com.google.common.base.Objects.equal(this.content, rhs.content)
            && com.google.common.base.Objects.equal(this.operation, rhs.operation)
            && com.google.common.base.Objects.equal(this.mapName, rhs.mapName);
    }

    public int hashCode() {
        return com.google.common.base.Objects.hashCode(this.content, this.operation, this.mapName);
    }

    public String toString() {
        return com.google.common.base.Objects.toStringHelper(this).addValue(this.getContent())
            .addValue(this.getOperation()).addValue(this.getMapName()).toString();
    }

}

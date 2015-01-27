/**
 * MassifSource
 * @year 2015
 * @author nsarychev
 */
package com.massifsource.sample.damp.common;

import java.io.Serializable;

public class JmsConfiguration implements Serializable {

    private static final long serialVersionUID = 1L;

    private String hostname;

    private String port;

    public JmsConfiguration() {
    }

    public JmsConfiguration(String hostname, String port) {
        super();
        this.setHostname(hostname);
        this.setPort(port);
    }

    public boolean equals(Object object) {
        if (!(object instanceof JmsConfiguration)) {
            return false;
        }
        JmsConfiguration rhs = (JmsConfiguration) object;
        return com.google.common.base.Objects.equal(this.hostname, rhs.hostname)
            && com.google.common.base.Objects.equal(this.port, rhs.port);
    }

    public int hashCode() {
        return com.google.common.base.Objects.hashCode(this.hostname, this.port);
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

}

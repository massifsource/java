/**
 * MassifSource
 * @year 2015
 * @author nsarychev
 */
package com.massifsource.sample.damp.ms.rest;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Required;

public class RestUrlProvider {

    private String hostname;
    private String port;
    private String restUrlBase;
    
    @PostConstruct
    public void buildUrl() {
        this.restUrlBase = String.format("http://%s:%s", this.hostname, this.port);
    }
    
    public String getRestUrlBase () {
        return this.restUrlBase;
    }
    
    public String getRestUrlBaseAndMapName (String mapName) {
        return this.restUrlBase + "/maps/" + mapName;
    }

    public String getHostname() {
        return hostname;
    }

    @Required
    public void setHostname(String hostname) {
        this.hostname = hostname.trim();
    }

    public String getPort() {
        return port;
    }

    @Required
    public void setPort(String port) {
        this.port = port.trim();
    }

}

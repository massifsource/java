/**
 * MassifSource
 * @year 2015
 * @author nsarychev
 */
package com.massifsource.sample.damp.ms.cli;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.core.CommandMarker;
import org.springframework.shell.core.annotation.CliAvailabilityIndicator;
import org.springframework.shell.core.annotation.CliCommand;
import org.springframework.shell.core.annotation.CliOption;
import org.springframework.stereotype.Component;

import com.massifsource.sample.damp.ms.cli.support.CommandSupportService;

@Component
public class Commands implements CommandMarker {

    @Autowired
    private CommandSupportService commandSupportService; 
    
    @CliCommand(value = { "configure rest source" }, help = "Configure REST settings for DAS source")
    public String configureRestSource(
        @CliOption(key = { "", "host" }, mandatory = true, help = "The IP address or hostname of DAS instance")
        String host,
        @CliOption(key = { "port" }, mandatory = false, help = "The port of REST interface", specifiedDefaultValue = "9090", unspecifiedDefaultValue = "9090")
        int port) {
        return commandSupportService.configureRestSource(host, port);
    }

    @CliCommand(value = { "configure rest destination" }, help = "Configure REST settings for DAS destination")
    public String configureRestDestination(
        @CliOption(key = { "", "host" }, mandatory = true, help = "The IP address or hostname of DAS instance")
        String host,
        @CliOption(key = { "port" }, mandatory = false, help = "The port of REST interface", specifiedDefaultValue = "9090", unspecifiedDefaultValue = "9090")
        int port) {
        return commandSupportService.configureRestDestination(host, port);
    }

    @CliCommand(value = { "configure jms" }, help = "Configure JMS settings for this instance")
    public String configureJms(
        @CliOption(key = { "", "host" }, mandatory = true, help = "The IP address of THIS instance")
        String host,
        @CliOption(key = { "port" }, mandatory = false, help = "The port to use for JMS traffic on this instance", specifiedDefaultValue = "9090", unspecifiedDefaultValue = "6161")
        int port) {
        return commandSupportService.configureJms(host, port);
    }

    @CliCommand(value = { "initialize", "init" }, help = "Initialize JMS and REST context")
    public String initializeContext() throws IOException {
        return commandSupportService.initializeContext();
    }

    @CliCommand(value = { "addlistener" }, help = "Add CRUD Listener to source DAS instance ")
    public String addListener(
        @CliOption(key = { "", "mapname" }, mandatory = true, help = "Map name to add listener to")
        String mapName) throws ClientProtocolException, IOException {
        return  String.format("Listener with id [%s] was added", commandSupportService.addListener(mapName));
    }
    
    @CliCommand(value = { "loadconfig" }, help = "Load configuration properties file")
    public String loadConfiguration (
        @CliOption(key = { "", "filepath" }, mandatory = false, help = "A path to properties file")
        String filePath) throws ClientProtocolException, IOException {
        return commandSupportService.loadConfigurationFile(filePath);
    }

    @CliCommand(value = { "removelistener" }, help = "Add CRUD Listener to source DAS instance ")
    public String removeListener(
        @CliOption(key = { "", "mapname" }, mandatory = true, help = "Map name to remove listener from")
        String mapName,
        @CliOption(key = { "", "id" }, mandatory = false, help = "id of listener to remove")
        String id) throws ClientProtocolException, IOException {
        return commandSupportService.removeListener(mapName, id);
    }

    @CliCommand(value = { "sync map" }, help = "Synchronize existing data in one map")
    public String syncMap(
        @CliOption(key = { "", "mapname" }, mandatory = true, help = "Name of the map to sync")
        String mapName) throws InterruptedException, ClientProtocolException, IOException {
        return commandSupportService.syncMap(mapName);
    }

    @CliCommand(value = { "sync all" }, help = "Synchronize existing data and enable replication")
    public String syncAll() throws InterruptedException, IOException {
        return commandSupportService.syncAll();
    }

    @CliCommand(value = { "remove all listeners" }, help = "Add CRUD Listener to source DAS instance ")
    public String removeAllListeners() throws InterruptedException, IOException {
        return commandSupportService.removeAllListeners();
    }
    

    @CliCommand(value = { "display config" }, help = "Display Configuration values")
    public String displayConfig() {
        return commandSupportService.displayConfig();
    }
    
    @CliAvailabilityIndicator({"sync all", "sync map", "remove all listeners", "removelistener", "addlistener"})
    public boolean isSyncCommandAvailable() {
      return commandSupportService.isInitialized();
    }
}

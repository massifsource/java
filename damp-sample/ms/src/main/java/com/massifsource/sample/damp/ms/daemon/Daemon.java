/**
 * MassifSource
 * @year 2015
 * @author nsarychev
 */
package com.massifsource.sample.damp.ms.daemon;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;

import com.massifsource.sample.damp.ms.cli.support.CommandSupportService;

public class Daemon {

    private static AbstractApplicationContext context;
    private static final Logger log = LoggerFactory.getLogger(Daemon.class);
    
    public static void main(String[] args) throws IOException, InterruptedException {
        System.setProperty("basedir", ".");
        DaemonContextProvider.getContext().registerShutdownHook();
        CommandSupportService service = DaemonContextProvider.getCommandSupportService();
        service.initializeContext();
        Runtime.getRuntime().addShutdownHook(new Thread(new DaemonTerminator(service)));
        log.info(service.syncAll());
        // keep running until terminated
        while(true) {
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (Exception e) {
                break;
            }
        }
    }

    public static ConfigurableApplicationContext getContext() {
        return context;
    }

}

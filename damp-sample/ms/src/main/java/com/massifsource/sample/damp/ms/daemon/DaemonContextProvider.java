/**
 * MassifSource
 * @year 2015
 * @author nsarychev
 */
package com.massifsource.sample.damp.ms.daemon;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.massifsource.sample.damp.ms.cli.support.CommandSupportService;

public class DaemonContextProvider {
    
private static final String[] CONFIG_LOCATIONS = { "classpath:spring/daemon-beans.xml" };
    
    private static AbstractApplicationContext applicationContext;
    
    public static synchronized AbstractApplicationContext getContext () {
        if (applicationContext == null) {
            applicationContext = new ClassPathXmlApplicationContext(CONFIG_LOCATIONS);
        }
        return applicationContext;
    }
    
    public static synchronized void destroyContext() {
        if (applicationContext != null) { 
            ((ClassPathXmlApplicationContext) applicationContext).close();
            applicationContext = null;
        }
    }
     
    public static CommandSupportService getCommandSupportService() {
        return getContext().getBean(CommandSupportService.class);
    }

}

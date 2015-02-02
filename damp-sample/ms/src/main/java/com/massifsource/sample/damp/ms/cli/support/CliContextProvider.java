/**
 * MassifSource
 * @year 2015
 * @author nsarychev
 */
package com.massifsource.sample.damp.ms.cli.support;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.massifsource.sample.damp.ms.jms.DataReplicationMessageListener;
import com.massifsource.sample.damp.ms.rest.RestService;
import com.massifsource.sample.damp.ms.rest.RestSynchronizer;

public class CliContextProvider {
    
private static final String[] CONFIG_LOCATIONS = { "classpath:spring/jms-beans.xml", "classpath:spring/rest-beans.xml", "classpath:spring/transformer-beans.xml" };
    
    private static ApplicationContext applicationContext;
    
    public static synchronized ApplicationContext getContext () {
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
    
    
    public static RestService getSourceRestService() {
        return getContext().getBean("sourceRestService", RestService.class);
    }
    
    public static RestService getDestinationRestService() {
        return getContext().getBean("destinationRestService", RestService.class);
    }
    
    public static RestSynchronizer getRestSynchronizer() {
        return getContext().getBean(RestSynchronizer.class);
    }
    
    public static DataReplicationMessageListener getDataReplicationMessageListener() {
        return getContext().getBean(DataReplicationMessageListener.class);
    }

}

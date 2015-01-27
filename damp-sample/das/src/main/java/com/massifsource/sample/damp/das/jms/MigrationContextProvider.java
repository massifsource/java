/**
 * MassifSource
 * @year 2015
 * @author nsarychev
 */
package com.massifsource.sample.damp.das.jms;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.hazelcast.core.EntryListener;

public class MigrationContextProvider {
    
    private static final String[] CONFIG_LOCATIONS = { "classpath:spring/migration-beans.xml" };
    
    private static ApplicationContext applicationContext;
        
    public static synchronized ApplicationContext initContext () {
        if (applicationContext == null) {
            applicationContext = new ClassPathXmlApplicationContext(CONFIG_LOCATIONS);
        }
        return applicationContext;
    }
    
    public static synchronized ApplicationContext getContext () throws MigrationContextIsNullException {
        if (applicationContext == null) {
            throw new MigrationContextIsNullException();
        }
        return applicationContext;
    }
    
    public static synchronized void destroyContext() {
        if (applicationContext != null) { 
            ((ClassPathXmlApplicationContext) applicationContext).close();
            applicationContext = null;
        }
    }
    
    @SuppressWarnings("unchecked")
    public static EntryListener<Object, Object> getEntryListener() throws BeansException, MigrationContextIsNullException {
        return getContext().getBean(EntryListener.class);
    }

}

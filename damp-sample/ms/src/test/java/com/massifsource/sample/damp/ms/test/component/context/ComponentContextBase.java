/**
 * MassifSource
 * @year 2015
 * @author nsarychev
 */
package com.massifsource.sample.damp.ms.test.component.context;

import static org.testng.Assert.assertNotNull;

import javax.management.MBeanServerConnection;
import javax.management.ObjectName;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.jms.connection.CachingConnectionFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;

import com.massifsource.sample.damp.ms.rest.RestService;

@ContextConfiguration({ "classpath:spring/rest-beans.xml", "classpath:spring/jms-beans.xml",
    "classpath:spring/test-beans.xml", "classpath:spring/transformer-beans.xml" })
public class ComponentContextBase extends AbstractTestNGSpringContextTests {

    protected static final int MAX_WAIT_TIMEOUT = 5000;
    protected static final int SLEEP_INTERVAL = 10;
    @Autowired
    protected JmsTemplate jmsTemplate;
    @Autowired
    protected RestService destinationRestService;
    @Autowired
    protected RestService sourceRestService;
    @Autowired
    protected ApplicationContext context;
    @Autowired
    protected CachingConnectionFactory connectionFactory;
    protected MBeanServerConnection mBeanServerConnection;
    
    Logger log = LoggerFactory.getLogger(this.getClass());
    
    @BeforeClass
    public void setUp() {
        assertNotNull(context);
        assertNotNull(destinationRestService);
        assertNotNull(sourceRestService);
        assertNotNull(jmsTemplate);
        ApplicationContext jmxContext = new ClassPathXmlApplicationContext("classpath:spring/test-jmx-beans.xml");
        assertNotNull(jmxContext);
        mBeanServerConnection = jmxContext.getBean(MBeanServerConnection.class);
        assertNotNull(mBeanServerConnection);
    }
    
    @AfterSuite
    public void destroyContext() {
        if (connectionFactory != null) connectionFactory.destroy();
        GenericApplicationContext genericContext = (GenericApplicationContext) context;
        if (genericContext != null) ((GenericApplicationContext) context).close();
    }
    
    protected Long getActiveMqJmxQueueSize() {
        Long queueSize = null;
        try {

            ObjectName objectNameRequest = new ObjectName(
                "org.apache.activemq:BrokerName=replicationBroker,Type=Queue,Destination="
                    + "com.massifsource.sample.damp.ms.jms.replicate");
            queueSize = (Long) mBeanServerConnection.getAttribute(objectNameRequest, "QueueSize");

            return queueSize;
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return queueSize;
    }
    
    protected void waitOnQueToBeEmpty() throws InterruptedException {
        int totalSleepTime = 0;
        while(getActiveMqJmxQueueSize() > 0) {
            if (totalSleepTime >= MAX_WAIT_TIMEOUT) break;
            Thread.sleep(SLEEP_INTERVAL);
            totalSleepTime += SLEEP_INTERVAL;
        }
        Thread.sleep(1000);
    }
}

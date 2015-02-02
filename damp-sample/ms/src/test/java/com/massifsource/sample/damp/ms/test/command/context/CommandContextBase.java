/**
 * MassifSource
 * @year 2015
 * @author nsarychev
 */
package com.massifsource.sample.damp.ms.test.command.context;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

import java.io.IOException;

import javax.management.MBeanServerConnection;
import javax.management.ObjectName;

import org.apache.http.client.ClientProtocolException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;

import com.massifsource.sample.damp.ms.cli.support.CliContextProvider;
import com.massifsource.sample.damp.ms.cli.support.CommandSupportService;
import com.massifsource.sample.damp.ms.rest.RestService;

@ContextConfiguration({ "classpath:spring/test-cli-beans.xml" })
public class CommandContextBase extends AbstractTestNGSpringContextTests {

    Logger log = LoggerFactory.getLogger(this.getClass());
    protected static final int MAX_WAIT_TIMEOUT = 5000;
    protected static final int SLEEP_INTERVAL = 10;
    
    @Autowired
    protected ApplicationContext context;

    @Autowired
    protected CommandSupportService commandSupportService;

    protected RestService destinationRestService;

    protected RestService sourceRestService;

    protected ApplicationContext jmxContext;

    protected MBeanServerConnection mBeanServerConnection;

    @BeforeClass
    public void testContextIsWired() throws Exception {
        assertNotNull(context);
        assertNotNull(commandSupportService);
        commandSupportService.loadConfigurationFile(null);
        commandSupportService.initializeContext();
        assertTrue(commandSupportService.checkConfig());
        this.destinationRestService = CliContextProvider.getDestinationRestService();
        assertNotNull(destinationRestService);
        this.sourceRestService = CliContextProvider.getSourceRestService();
        assertNotNull(sourceRestService);
        
        // load JMX context manually after activeMQ has been started
        jmxContext = new ClassPathXmlApplicationContext("classpath:spring/test-jmx-beans.xml");
        assertNotNull(jmxContext);
        mBeanServerConnection = jmxContext.getBean(MBeanServerConnection.class);
        assertNotNull(mBeanServerConnection);
    }

    @AfterSuite
    public void destroyContext() {
        GenericApplicationContext genericContext = (GenericApplicationContext) context;
        if (genericContext != null) ((GenericApplicationContext) context).close();
    }

    @AfterClass
    public void cleanUp() throws ClientProtocolException, IOException {
        commandSupportService.removeAllListeners();
        sourceRestService.deleteAll("product_state");
        destinationRestService.deleteAll("product_state");
        sourceRestService.deleteAll("subscription");
        destinationRestService.deleteAll("subscription");
        assertEquals(sourceRestService.getAll("product_state").length(), 0);
        assertEquals(destinationRestService.getAll("product_state").length(), 0);
        assertEquals(sourceRestService.getAll("subscription").length(), 0);
        assertEquals(destinationRestService.getAll("subscription").length(), 0);
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
            logger.error(e.getMessage());
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

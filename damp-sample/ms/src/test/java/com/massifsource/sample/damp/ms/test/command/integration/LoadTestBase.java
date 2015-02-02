/**
 * MassifSource
 * @year 2015
 * @author nsarychev
 */
package com.massifsource.sample.damp.ms.test.command.integration;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeClass;

import com.massifsource.sample.damp.ms.test.command.context.CommandContextBase;
import com.massifsource.sample.damp.ms.test.common.RunnableDataCreator;
import com.massifsource.sample.damp.ms.test.common.TestDataProvider;

public class LoadTestBase extends CommandContextBase {

    protected static final int SLEEP_INTERVAL = 1000;

    protected static final int MAX_WAIT_TIMEOUT = 60000;

    protected static final int GENERATOR_ITERATIONS_NUM = 2000;

    protected static final int GENERATOR_SLEEP_INTERVAL_MILLIS = 1;

    protected static final int INITIAL_DATA_SIZE = 5000;

    protected Logger log = LoggerFactory.getLogger(this.getClass());

    @BeforeClass
    public void prepareData() throws ClientProtocolException, IOException, InterruptedException {
        assertEquals(sourceRestService.getCount("product_state"), 0);

        logInitialData("product_state");
        TestDataProvider.populateProductState(INITIAL_DATA_SIZE, sourceRestService, 9679);
        
        assertEquals(sourceRestService.getCount("product_state"),
            INITIAL_DATA_SIZE);
        assertEquals(destinationRestService.getCount("product_state"), 0);
    }

    protected void logCounts(String mapName) throws ClientProtocolException, IOException {
        log.info("\t{}: source [{}], destination [{}]", mapName, sourceRestService.getCount(mapName), destinationRestService.getCount(mapName));
    }

    protected void logCounts() throws ClientProtocolException, IOException {
        log.info("Row counts: ");
        logCounts("product_state");
    }

    protected void logInitialData(String mapName) throws ClientProtocolException, IOException {
        log.info("Generating initial {} rows for {} source", INITIAL_DATA_SIZE, mapName);
    }

    protected void runSyncAll() throws InterruptedException, IOException {
        RunnableDataCreator dataCreator = new RunnableDataCreator(GENERATOR_ITERATIONS_NUM,
            GENERATOR_SLEEP_INTERVAL_MILLIS, sourceRestService);
        Thread createThread = new Thread(dataCreator);
        createThread.start();
        // Create another identical thread, to attempt to cause
        // collisions/updates
        Thread collisionThread = new Thread(dataCreator);
        collisionThread.start();
        log.info("Started dataCreator and collision threads");
        log.info("Running sync");
        commandSupportService.syncAll();
        log.info("Finished sync");
        int currentWait = 0;
        boolean timeout = false;
        while (createThread.isAlive() || collisionThread.isAlive()) {
            log.info("Waiting on dataCreator and collison threads to finish");
            logCounts();
            if (currentWait >= MAX_WAIT_TIMEOUT) {
                timeout = true;
                break;
            }
            currentWait += SLEEP_INTERVAL;
            Thread.sleep(SLEEP_INTERVAL);
        }
        assertFalse(timeout);
        log.info("dataCreator and collison threads finished");
        currentWait = 0;
        timeout = true;
        while (currentWait < MAX_WAIT_TIMEOUT) {
            Long queueSize = getActiveMqJmxQueueSize();
            log.info("QUEUE_SIZE: {}", queueSize);
            if (queueSize != null && queueSize.equals(0L)) {
                timeout = false;
                break;
            }
            log.info("Waiting on queue finish");
            logCounts();
            currentWait += SLEEP_INTERVAL;
            Thread.sleep(SLEEP_INTERVAL);
        }
        assertFalse(timeout);
    }

}

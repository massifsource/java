/**
 * MassifSource
 * @year 2015
 * @author nsarychev
 */
package com.massifsource.sample.damp.ms.test.daemon.integration;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;

import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeoutException;

import org.apache.http.client.ClientProtocolException;
import org.springframework.context.ConfigurableApplicationContext;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.massifsource.sample.damp.ms.daemon.Daemon;
import com.massifsource.sample.damp.ms.test.command.integration.LoadTestBase;
import com.massifsource.sample.damp.ms.test.common.RunnableDataCreator;

public class DaemonTest extends LoadTestBase {

    private static ConfigurableApplicationContext context;

    @BeforeClass
    public static void startUp() throws InterruptedException, IOException, ExecutionException,
        TimeoutException {
        //System.setProperty("basedir", "target");
    }

    @Test
    public void testDaemon() throws ClientProtocolException, IOException, InterruptedException {
        assertEquals(0, 0);
        RunnableDataCreator dataCreator = new RunnableDataCreator(GENERATOR_ITERATIONS_NUM,
            GENERATOR_SLEEP_INTERVAL_MILLIS, sourceRestService);
        Thread createThread = new Thread(dataCreator);
        createThread.start();
        // Create another identical thread, to attempt to cause
        // collisions/updates
        Thread collisionThread = new Thread(dataCreator);
        collisionThread.start();
        
        Executors.newSingleThreadExecutor().submit(new Callable<ConfigurableApplicationContext>() {
            String[] args = { "" };

            public ConfigurableApplicationContext call() throws IOException, InterruptedException {
                Daemon.main(args);
                return Daemon.getContext();
            }
        });
        
        context = Daemon.getContext();
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

    @AfterClass
    public static void shutdown() {

        if (context != null) {
            context.close();
        }
    }
}

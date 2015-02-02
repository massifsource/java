/**
 * MassifSource
 * @year 2015
 * @author nsarychev
 */
package com.massifsource.sample.damp.ms.test.integration.instance;

import static org.testng.Assert.assertNotNull;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import com.google.common.io.Files;
import com.massifsource.sample.damp.das.App;

public class StartInstancesTest extends AbstractTestNGSpringContextTests {

    private static final Logger log = LoggerFactory.getLogger(StartInstancesTest.class);
    private static final String TARGET_RESOURCES_DIR = System.getProperty("basedir") + "/target/test-classes/";
    private static final String SOURCE_RESOURCES_DIR = System.getProperty("basedir") + "/src/test/resources/das/";
    private static final String SEPARATOR = "----------------------------------------";
    private static ConfigurableApplicationContext sourceInstanceContext;
    private static ConfigurableApplicationContext targetInstanceContext;
    
    @BeforeSuite
    public void prepTestDirectories () {
        File targetResourceDir = new File(TARGET_RESOURCES_DIR);
        targetResourceDir.mkdirs();
    }
    
    @BeforeSuite(dependsOnMethods = {"startTargetInstance"})
    public void testContextsAreLoaded () {
        assertNotNull(sourceInstanceContext);
        assertNotNull(targetInstanceContext);
    }
    
    @BeforeSuite(dependsOnMethods = {"prepTestDirectories"})
    public static void startSourceInstance() throws Exception {
        log.info(SEPARATOR);
        log.info("Starting Source DAS Instance");
        log.info(SEPARATOR);
        Files.copy(new File(SOURCE_RESOURCES_DIR + "application-source.yml"), new File(TARGET_RESOURCES_DIR + "application.yml"));
        Files.copy(new File(SOURCE_RESOURCES_DIR + "hazelcast-instance-source.properties"), new File(TARGET_RESOURCES_DIR + "hazelcast-instance.properties"));
        String[] args = { "" };
        com.massifsource.sample.damp.das.App.main(args);
        sourceInstanceContext = App.getContext();
    }
    
    @BeforeSuite(dependsOnMethods = {"startSourceInstance"})
    public static void startTargetInstance() throws Exception {
        log.info(SEPARATOR);
        log.info("Starting Target DAS Instance");
        log.info(SEPARATOR);
        Files.copy(new File(SOURCE_RESOURCES_DIR + "application-target.yml"), new File(TARGET_RESOURCES_DIR + "application.yml"));
        Files.copy(new File(SOURCE_RESOURCES_DIR + "hazelcast-instance-target.properties"), new File(TARGET_RESOURCES_DIR + "hazelcast-instance.properties"));
        String[] args = { "" };
        com.massifsource.sample.damp.das.App.main(args);
        targetInstanceContext = App.getContext();
    }
    
    
    @AfterSuite(alwaysRun = true)
    public static void stopInstanceOne() {
        log.info(SEPARATOR);
        log.info("Destroying Source DAS Instance");
        log.info(SEPARATOR);
        if (sourceInstanceContext != null) {
            sourceInstanceContext.close();
        }
    }
    
    @AfterSuite(alwaysRun = true)
    public static void stopInstanceTwo() {
        log.info(SEPARATOR);
        log.info("Destroying Target DAS Instance");
        log.info(SEPARATOR);
        if (targetInstanceContext != null) {
            targetInstanceContext.close();
        }
    }
}

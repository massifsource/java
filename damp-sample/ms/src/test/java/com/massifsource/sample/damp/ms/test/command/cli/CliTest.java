/**
 * MassifSource
 * @year 2015
 * @author nsarychev
 */
package com.massifsource.sample.damp.ms.test.command.cli;
import static org.testng.Assert.assertEquals;

import java.io.IOException;

import org.springframework.shell.Bootstrap;
import org.springframework.shell.core.JLineShellComponent;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class CliTest {
        
    
    private static JLineShellComponent shell;
    
    @BeforeClass
    public static void startUp() throws InterruptedException, IOException {
        System.setProperty("basedir", "target");
        Bootstrap bootstrap = new Bootstrap();
        shell = bootstrap.getJLineShellComponent();
    }
    
    @Test
    public void testCli() {
        assertEquals(shell.executeCommand("loadconfig").getResult().toString(), "Loaded conf/env.properties");
        assertEquals(shell.executeCommand("init").getResult().toString(), "Context initialized");
        assertEquals(shell.executeCommand("sync all").getResult().toString(), "Sync completed for all maps");
    }
    
    @AfterClass
    public static void shutdown() {
        shell.stop();
    }
}

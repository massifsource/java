/**
 * MassifSource
 * @year 2015
 * @author nsarychev
 */
package com.massifsource.sample.damp.das;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration
@ComponentScan
public class App {
    
    private static ConfigurableApplicationContext context;

    public static void main(String[] args) {
        String [] configs = {"classpath:/spring/embedded-jetty-context.xml", "classpath:spring/hazelcast-beans.xml"};
        context = SpringApplication.run(configs, args);
    }
    
    public static ConfigurableApplicationContext getContext () {
        return context;
    }

}

/**
 * MassifSource
 * @year 2015
 * @author nsarychev
 */
package com.massifsource.sample.damp.das.web;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.LifecycleService;
import com.hazelcast.core.Member;
import com.massifsource.sample.damp.common.JmsConfiguration;
import com.massifsource.sample.damp.das.jms.MigrationContextProvider;

@RestController
public class InstanceController {
    
    @Autowired
    private HazelcastInstance hazelcast;

        @RequestMapping("/")
        public String display() {
                return "Hazelcast Instance";
        }
        
        @RequestMapping("/status")
        public LifecycleService getStatus() {
                return hazelcast.getLifecycleService();
        }
        
        @RequestMapping("/cluster/members")
        public Set<Member> getMembers() {
            return hazelcast.getCluster().getMembers();
        }
        
        @RequestMapping("/cluster/time")
        public long getTime() {
            return hazelcast.getCluster().getClusterTime();
        }
        
        @RequestMapping(value = "/migration/jms", method = RequestMethod.POST)
        public void configureJms(@RequestBody JmsConfiguration jmsConfiguration) {
            String jmsBrokerUrl = String.format("tcp://%s:%s", jmsConfiguration.getHostname().trim(),jmsConfiguration.getPort().trim()); 
            System.setProperty("jms.broker.url", jmsBrokerUrl);
            MigrationContextProvider.destroyContext();
            MigrationContextProvider.initContext();
        }

}
/**
 * MassifSource
 * @year 2015
 * @author nsarychev
 */
package com.massifsource.sample.damp.das.hazelcast.support;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.client.impl.HazelcastClientProxy;
import com.hazelcast.core.HazelcastInstance;

public class HazelcastInstanceProvider {

	@Autowired
	private HazelcastInstance instance;
	private ClientConfig clientConfig;
	private static final Logger log = LoggerFactory
			.getLogger(HazelcastInstanceProvider.class);

	@PostConstruct
	public void getConfig() {
		if (instance instanceof HazelcastClientProxy
				&& instance.getLifecycleService().isRunning()) {
			clientConfig = ((HazelcastClientProxy) instance).getClientConfig();
		}
	}

	public HazelcastInstance getInstance() {
		if (instance instanceof HazelcastClientProxy
				&& !instance.getLifecycleService().isRunning()) {
			log.warn("Hazelcast client is no longer running. Building new client.");
			instance.shutdown();
			instance = HazelcastClient.newHazelcastClient(clientConfig);
			/*
			 * String beanName = "hazelcastClient"; DefaultListableBeanFactory
			 * defaultListableBeanFactory = (DefaultListableBeanFactory) context
			 * .getAutowireCapableBeanFactory(); BeanDefinition beanDefinition =
			 * defaultListableBeanFactory .getBeanDefinition(beanName); //
			 * beanDefinition.setBeanClassName(HazelcastInstance.class); try {
			 * defaultListableBeanFactory.registerBeanDefinition(beanName,
			 * beanDefinition); } catch (BeanCreationException e) {
			 * log.error("Failed to rebuild HazelcastClient bean"); }
			 */
		}
		return instance;
	}

	public void setInstance(HazelcastInstance instance) {
		this.instance = instance;
	}

}

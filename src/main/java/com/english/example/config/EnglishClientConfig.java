package com.english.example.config;

import org.springframework.context.annotation.Bean;

import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.IPing;
import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.PingUrl;
import com.netflix.loadbalancer.WeightedResponseTimeRule;



public class EnglishClientConfig {

	/*
	 * if we implement hystrx, then we better use AvailabilityFiltering rules since 
	 * it works with circuit breaker. But for now, we use response time rule because 
	 * hystrix is not used here at the moment.
	 */
	@Bean
	public IRule ribbonRule(IClientConfig config) {
		return new WeightedResponseTimeRule();
	}
	
	@Bean
	public IPing ribbonPing(IClientConfig config) {
		return new PingUrl(false, "/");
	}

	
	
	
	
}

package com.english.example;

import java.time.Duration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableEurekaClient
public class EurekaClientWith2Application {

	
	public static void main(String[] args) {
		SpringApplication.run(EurekaClientWith2Application.class, args);
	}
	
	/**
	 * provide two different rest template for two controllers.
	 * This one is load-balanced rest template
	 * @return
	 */
	@Bean
	@LoadBalanced
	public RestTemplate loadBalanced() {
		
		return new RestTemplateBuilder().setConnectTimeout(Duration.ofMillis(5000))
										.setReadTimeout(Duration.ofMillis(5000))
										.build();
	}
	
	
	/**
	 * this is a normal rest template
	 * @return
	 */
	@Primary
	@Bean
	public RestTemplate restTemplate() {
		
		return new RestTemplateBuilder().setConnectTimeout(Duration.ofMillis(5000))
										.setReadTimeout(Duration.ofMillis(5000))
										.build();
	}
	
	
}

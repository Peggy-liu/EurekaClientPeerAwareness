package com.english.example.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;


@RestController
@Profile("english")
public class HelloController {
	
	private static Logger log = LoggerFactory.getLogger(HelloController.class);


	 @Autowired
	 private RestTemplate restTemplate;
	
	
	
	
//	@Autowired
//	private EurekaClient discoveryClient;
	
	@Value("${tpd.appconfig.spanish-alias}")
	private String spanish_alias;
	
	
	@GetMapping("/")
	public String home() {
		log.info("Access2 /");
		return "hi";
	}
	
	@GetMapping("/hello-server")
	public String helloServer() {
		log.info("hit the english server");
		return "Hello from the English client!";
	}
	
	@GetMapping("/hello")
	public String hello() {
		log.info("hit the english server");
		//final InstanceInfo instance = discoveryClient.getNextServerFromEureka(spanish_alias, false);
		//String url = instance.getHomePageUrl();
		String response = restTemplate.getForObject("http://demo-tpd-es/halo-server", String.class);
		return  " My spanish peer said :" + response ;
	}
	
	
}

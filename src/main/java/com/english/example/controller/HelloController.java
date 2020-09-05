package com.english.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;


@RestController
@Profile("english")
public class HelloController {
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private EurekaClient discoveryClient;
	
	@Value("${tpd.appconfig.spanish-alias}")
	private String spanish_alias;
	
	
	
	@GetMapping("/hello-server")
	public String helloServer() {
		return "Hello from the English client!";
	}
	
	@GetMapping("/hello")
	public String hello() {
		final InstanceInfo instance = discoveryClient.getNextServerFromEureka(spanish_alias, false);
		String url = instance.getHomePageUrl();
		String response = restTemplate.getForObject(url+"/halo-server", String.class);
		return helloServer() + " My spanish peer said :" + response;
	}
	
	
}

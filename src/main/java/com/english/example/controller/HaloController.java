package com.english.example.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;

@RestController
@Profile("spanish")
public class HaloController {

	private static Logger log = LoggerFactory.getLogger(HaloController.class);
	@Autowired
	private RestTemplate restTemplate;
	
	@Value("${server.port}")
	String port;
	
	@Autowired
	private EurekaClient discoveryClient;
	
	@Value("${tpd.appconfig.english-alias}")
	private String english_alias;
	
	@GetMapping("/")
	public String home() {
		log.info("Access /");
		return "hi";
	}
	@GetMapping("/halo-server")
	public String HaloServer() {
		return "Hola desde el cliente en Español!";
	}
	
	@GetMapping("/halo")
	public String Halo() {
		final InstanceInfo instance = discoveryClient.getNextServerFromEureka(english_alias, false);
		String url = instance.getHomePageUrl();
		String response = restTemplate.getForObject(url+"/hello-server", String.class);
		//return HaloServer() +" My English peer said "+ response + instance.getPort();
		return HaloServer() + "My port is "+ port +" and im calling english on "+ instance.getPort();
	}
}

package com.english.example.controller;

import java.net.URI;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.english.example.config.SpanishClientConfig;

/**
 * This controller class uses "RestTemplate + Ribbon API" to perform
 * client side load balancing. 
 * The concrete implementation of "LoadBalancerClient"
 * is "RibbonLoadBalancerClient"class provided by "Spring Cloud Netflix Ribbon" package.
 * An alternative solution can use "BlockingLoadBalancerClient" class provided by 
 * "Spring Cloud Client Loadbalancer", simply by swapping the dependency on 
 * the class path.
 * @author liupeiqi
 *
 */
@RestController
@Profile("english")
@RibbonClient(name="demo-tpd-es", configuration=SpanishClientConfig.class)
public class HelloController {
	
	private static Logger log = LoggerFactory.getLogger(HelloController.class);

    @Autowired
	private RestTemplate restTemplate;
    
    @Autowired
    private LoadBalancerClient loadBalancer;
	
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
		URI address = this.loadBalance("demo-tpd-es");
		String url = String.format("%s%s", address,"/halo-server");
		String response = restTemplate.getForObject(url, String.class);
		
		return  " My spanish peer said :" + response ;
	}
	
	/**
	 * perform load balancing and choose a server 
	 * @param serviceId
	 * @return instance URI
	 */
	protected URI loadBalance(String serviceId) {
		ServiceInstance instance = loadBalancer.choose(serviceId);
		URI result = instance.getUri();
		return result;
	}
	
}

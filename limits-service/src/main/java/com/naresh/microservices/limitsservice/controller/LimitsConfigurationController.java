package com.naresh.microservices.limitsservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.naresh.microservices.limitsservice.Configuration;
import com.naresh.microservices.limitsservice.bean.LimitConfiguration;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.ribbon.proxy.annotation.Hystrix;

@RestController
public class LimitsConfigurationController {

	@Autowired
	Configuration config;

	@GetMapping("/limits")
	public LimitConfiguration retrieveLimitsFromConfigurations() {
		return new LimitConfiguration(config.getMaximum(), config.getMinimum());
	}
	
	@GetMapping("/fault-example")
	@HystrixCommand(fallbackMethod = "fallbackRetrieve")
	public LimitConfiguration retrievefault() {
		throw new RuntimeException("not available");
	}
	
	public LimitConfiguration fallbackRetrieve() {
		return new LimitConfiguration(0, 0);
	}

}

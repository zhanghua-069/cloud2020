package com.fleexy.springcloud.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class OrderConsulController {

    private static final String SERVICE_URL = "http://consul-provider-payment";

    @Autowired
    RestTemplate restTemplate;

    @GetMapping("/consumer/payment/consul")
    public String payment() {
        return restTemplate.getForObject(SERVICE_URL + "/payment/consul", String.class);
    }

}

package com.fleexy.springcloud.controller;

import com.fleexy.springcloud.entities.CommonResult;
import com.fleexy.springcloud.entities.Payment;
import com.fleexy.springcloud.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
@RestController
public class PaymentController {

    @Autowired
    PaymentService paymentService;
    @Value("${server.port}")
    private String serverPort;

    @Autowired
    DiscoveryClient discoveryClient;

    @PostMapping("/payment/add")
    public CommonResult addPayment(@RequestBody Payment payment) {
        int result = paymentService.addPayment(payment);
        if(result > 0) {
            return new CommonResult(200, "插入数据成功, serverPort=" + serverPort, result);
        } else {
            return new CommonResult(500, "插入数据失败");
        }
    }

    @GetMapping("/payment/get/{id}")
    public CommonResult getPaymentById(@PathVariable("id") Long id) {
        Payment payment = paymentService.getPaymentById(id);
        if(payment != null) {
            return new CommonResult(200, "数据查询成功, serverPort=" + serverPort, payment);
        } else {
            return new CommonResult(500, "数据查询失败，id=" + id);
        }
    }

    @GetMapping("/payment/discovery")
    public Object discovery() {
        // 获取注册到eureka中的微服务的服务信息
        List<String> services = discoveryClient.getServices();
        for (String service : services) {
            log.info("service=" + service);
        }

        // 获取具体服务的实例信息
        List<ServiceInstance> instances = discoveryClient.getInstances("CLOUD-PAYMENT-SERVICE");
        for (ServiceInstance instance : instances) {
            log.info(instance.getInstanceId() + "\t" + instance.getHost() + "\t" + instance.getPort() + "\t" + instance.getUri());
        }
        return discoveryClient;
    }

    @GetMapping(value = "/payment/lb")
    public String getPaymentLB(){
        return serverPort;
    }

    @GetMapping("/payment/feign/timeout")
    public String paymentFeignTimeout() {
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return serverPort;
    }

    @GetMapping("/payment/zipkin")
    public String paymentZipkin()
    {
        return "hi ,i'am paymentzipkin server fall back，welcome to atguigu，O(∩_∩)O哈哈~";
    }

}

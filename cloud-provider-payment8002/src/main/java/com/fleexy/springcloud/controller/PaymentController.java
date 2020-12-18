package com.fleexy.springcloud.controller;

import com.fleexy.springcloud.entities.CommonResult;
import com.fleexy.springcloud.entities.Payment;
import com.fleexy.springcloud.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.TimeUnit;

@Slf4j
@RestController
public class PaymentController {

    @Autowired
    PaymentService paymentService;
    @Value("${server.port}")
    private String serverPort;

    @PostMapping("/payment/add")
    public CommonResult addPayment(@RequestBody Payment payment) {
        int result = paymentService.addPayment(payment);
        if(result > 0) {
            return new CommonResult(200, "插入数据成功,serverPort=" + serverPort, result);
        } else {
            return new CommonResult(500, "插入数据失败");
        }
    }

    @GetMapping("/payment/get/{id}")
    public CommonResult getPaymentById(@PathVariable("id") Long id) {
        Payment payment = paymentService.getPaymentById(id);
        if(payment != null) {
            return new CommonResult(200, "数据查询成功,serverPort=" + serverPort, payment);
        } else {
            return new CommonResult(500, "数据查询失败，id=" + id);
        }
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
}

package com.fleexy.springcloud.service;

import com.fleexy.springcloud.entities.Payment;

public interface PaymentService {

    public int addPayment(Payment payment);

    public Payment getPaymentById(Long id);
}

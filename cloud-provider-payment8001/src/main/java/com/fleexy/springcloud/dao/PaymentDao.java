package com.fleexy.springcloud.dao;

import com.fleexy.springcloud.entities.Payment;
import org.apache.ibatis.annotations.Param;

public interface PaymentDao {

    public int addPayment(Payment payment);

    public Payment getPaymentById(@Param("id") Long id);
}

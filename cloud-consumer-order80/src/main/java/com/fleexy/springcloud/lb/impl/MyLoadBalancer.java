package com.fleexy.springcloud.lb.impl;

import com.fleexy.springcloud.lb.LoadBalancer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Component
public class MyLoadBalancer implements LoadBalancer {

    private AtomicInteger serviceIndex = new AtomicInteger(0);

    @Override
    public ServiceInstance instances(List<ServiceInstance> instances) {
        int index = incrementAndGet() % instances.size();// 得到服务器的位置下标
        return instances.get(index);
    }

    private final int incrementAndGet() {
        int current;
        int next;

        do {
            current = serviceIndex.get();
            // cas操作获取访问次数
            next = current >= Integer.MAX_VALUE ? 0 : current + 1;
        }while (!serviceIndex.compareAndSet(current, next));// 直到next被设置成功，否则将一直在循环体内做cas操作
        log.info("第" + next + "次访问");
        return next;
    }
}

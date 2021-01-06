package com.fleexy.springcloud.service;

import com.fleexy.springcloud.util.IdGeneratorSnowFlake;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
@Service
public class OrderService {

    @Autowired
    IdGeneratorSnowFlake idGenerator;

    public String getIdBySnowFlake() {
        ExecutorService threadPool = Executors.newFixedThreadPool(5);
        // 多线程生成20个id
        for (int i=0; i < 20; i++) {
            threadPool.submit(() -> {
                log.info(idGenerator.getSnowFlakeId() + "");
            });
        }
        threadPool.shutdown();

        return "hello snowflake";
    }

}

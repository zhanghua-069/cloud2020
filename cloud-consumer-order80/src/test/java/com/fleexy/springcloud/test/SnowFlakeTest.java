package com.fleexy.springcloud.test;

import com.fleexy.springcloud.service.OrderService;
import com.fleexy.springcloud.util.IdGeneratorSnowFlake;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class SnowFlakeTest {

    @Autowired
    IdGeneratorSnowFlake idGenerator;
    @Autowired
    OrderService orderService;

    @Test
    public void testGetSnowFlakeId() {
        long id1 = idGenerator.getSnowFlakeId(1, 1);
        log.info("id1=" + id1);
        long id2 = idGenerator.getSnowFlakeId();
        log.info("id2=" + id2);
    }

    @Test
    public void testGetIdBySnowFlake() {
        String msg = orderService.getIdBySnowFlake();
        log.info(msg);
    }
}

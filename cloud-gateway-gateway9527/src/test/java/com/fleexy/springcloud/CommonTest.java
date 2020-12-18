package com.fleexy.springcloud;

import org.junit.Test;

import java.time.ZonedDateTime;

public class CommonTest {

    @Test
    public void test() {
        ZonedDateTime now = ZonedDateTime.now();
        System.out.println(now);
    }
}

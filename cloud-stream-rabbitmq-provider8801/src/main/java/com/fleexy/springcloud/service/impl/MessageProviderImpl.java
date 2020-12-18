package com.fleexy.springcloud.service.impl;

import cn.hutool.core.util.IdUtil;
import com.fleexy.springcloud.service.IMessageProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;

/**
 * 定义消息（生产者）的推送管道
 */
@Slf4j
@EnableBinding(Source.class)// 开启绑定器
public class MessageProviderImpl implements IMessageProvider {

    @Autowired
    private MessageChannel output;// 消息发送管道

    @Override
    public String send() {
        String uuid = IdUtil.simpleUUID();
        output.send(MessageBuilder.withPayload(uuid).build());// 发送消息
        log.info("****** uuid：" + uuid + " ******");
        return uuid;
    }
}

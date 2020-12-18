package com.fleexy.springcloud.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

// 定义消费接收通道
@Slf4j
@Component
@EnableBinding(Sink.class)
public class ReceiveMessageListener {

    @Value("${server.port}")
    private String serverPort;

    @StreamListener(Sink.INPUT)// 监听队列，用于消费者队列接收消息
    public void input(Message<String> message) {
        log.info("消费者2号，接受："+message.getPayload()+"\t port:"+serverPort);
    }
}

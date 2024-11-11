package com.example.mq.topic;

import com.example.mq.ConnectionUtil;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;

@Slf4j
public class Producer {

    public static final String TOPIC_EXCHANGE = "topic_exchange";

    public static final String TOPIC_QUEUE_1 = "topic_queue_1";

    public static final String TOPIC_QUEUE_2 = "topic_queue_2";

    public static void main(String[] args) throws Exception {

        final Connection connection = ConnectionUtil.getConnection();
        final Channel channel = connection.createChannel();

        // 声明交换机
        channel.exchangeDeclare(TOPIC_EXCHANGE, BuiltinExchangeType.TOPIC);

        String message = "新增了商品，Topic模式；routing key为 item.insert";
        channel.basicPublish(TOPIC_EXCHANGE, "item.insert", null, message.getBytes(StandardCharsets.UTF_8));
        log.info(">>>>>>>>>>> send message: {}", message);

        message = "修改了商品，Topic模式；routing key 为 item.update";
        channel.basicPublish(TOPIC_EXCHANGE, "item.update", null, message.getBytes(StandardCharsets.UTF_8));
        log.info(">>>>>>>>>>> send message: {}", message);

        message = "删除了商品，Topic模式；routing key为 item.delete";
        channel.basicPublish(TOPIC_EXCHANGE, "item.delete", null, message.getBytes(StandardCharsets.UTF_8));
        log.info(">>>>>>>>>>> send message: {}", message);

        channel.close();
        connection.close();
    }
}

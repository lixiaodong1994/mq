package com.example.mq.routing;

import com.example.mq.ConnectionUtil;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;

@Slf4j
public class Producer {

    public static final String DIRECT_EXCHANGE = "direct_exchange";

    public static final String DIRECT_QUEUE_INSERT = "direct_queue_insert";

    public static final String DIRECT_QUEUE_UPDATE = "direct_queue_update";

    public static void main(String[] args) throws Exception {

        final Connection connection = ConnectionUtil.getConnection();
        final Channel channel = connection.createChannel();

        // 声明交换机
        channel.exchangeDeclare(DIRECT_EXCHANGE, BuiltinExchangeType.DIRECT);

        // 声明队列
        channel.queueDeclare(DIRECT_QUEUE_INSERT, true, false, false, null);
        channel.queueDeclare(DIRECT_QUEUE_UPDATE, true, false, false, null);

        // 队列绑定交换机
        channel.queueBind(DIRECT_QUEUE_INSERT, DIRECT_EXCHANGE, "insert");
        channel.queueBind(DIRECT_QUEUE_UPDATE, DIRECT_EXCHANGE, "update");

        String message = "新增了商品，路由模式；routing key 为 insert";

        channel.basicPublish(DIRECT_EXCHANGE, "insert", null, message.getBytes(StandardCharsets.UTF_8));
        log.info(">>>>>>>>>>> send message: {}", message);

        message = "修改了商品，路由模式；routing key为 update";

        channel.basicPublish(DIRECT_EXCHANGE, "update", null, message.getBytes(StandardCharsets.UTF_8));
        log.info(">>>>>>>>>> send message: {}", message);

        channel.close();
        connection.close();
    }

}

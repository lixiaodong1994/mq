package com.example.mq.subscribe;

import com.example.mq.ConnectionUtil;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;

@Slf4j
public class Producer {

    public static final String FANOUT_EXCHANGE = "fanout_exchange";

    public static final String FANOUT_QUEUE_1 = "fanout_queue_1";

    public static final String FANOUT_QUEUE_2 = "fanout_queue_2";

    public static void main(String[] args) throws Exception {

        final Connection connection = ConnectionUtil.getConnection();
        final Channel channel = connection.createChannel();

        // 声明交换机
        channel.exchangeDeclare(FANOUT_EXCHANGE, BuiltinExchangeType.FANOUT);

        // 声明队列
        channel.queueDeclare(FANOUT_QUEUE_1, true, false, false, null);
        channel.queueDeclare(FANOUT_QUEUE_2, true, false, false, null);

        channel.queueBind(FANOUT_QUEUE_1, FANOUT_EXCHANGE, "");
        channel.queueBind(FANOUT_QUEUE_2, FANOUT_EXCHANGE, "");

        for (int i = 0; i < 30; i++) {

            String message = "hello subscribe queue " + i;

            channel.basicPublish(FANOUT_EXCHANGE, "", null, message.getBytes(StandardCharsets.UTF_8));
            log.info(">>>>>>>>>>> send message: {}", message);
        }

        channel.close();
        connection.close();
    }
}

package com.example.mq.work;

import com.example.mq.ConnectionUtil;
import com.rabbitmq.client.*;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
public class Consumer1 {

    public static void main(String[] args) throws Exception {

        final Connection connection = ConnectionUtil.getConnection();
        final Channel channel = connection.createChannel();

        channel.queueDeclare(Producer.QUEUE_NAME, true, false, false, null);

        DefaultConsumer consumer = new DefaultConsumer(channel) {
            /**
             * No-op implementation of {@link Consumer#handleDelivery}.
             *
             * @param consumerTag
             * @param envelope
             * @param properties
             * @param body
             */
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                log.info(">>>>>>>>>>> 路由key: {}", envelope.getRoutingKey());
                log.info(">>>>>>>>>>> 交换机：{}", envelope.getExchange());
                log.info(">>>>>>>>>>> 消息id：{}", envelope.getDeliveryTag());
                log.info(">>>>>>>>>>> consumer1接收的消息：{}", new String(body));
            }
        };

        channel.basicConsume(Producer.QUEUE_NAME, true, consumer);
    }
}

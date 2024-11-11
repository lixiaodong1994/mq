package com.example.mq.topic;

import com.example.mq.ConnectionUtil;
import com.rabbitmq.client.*;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
public class Consumer2 {

    public static void main(String[] args) throws Exception {

        final Connection connection = ConnectionUtil.getConnection();
        final Channel channel = connection.createChannel();

        // 声明交换机
        channel.exchangeDeclare(Producer.TOPIC_EXCHANGE, BuiltinExchangeType.TOPIC);

        // 声明队列
        channel.queueDeclare(Producer.TOPIC_QUEUE_2, true, false, false, null);

        // 队列绑定交换机
        channel.queueBind(Producer.TOPIC_QUEUE_2, Producer.TOPIC_EXCHANGE, "item.#");

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
                log.info(">>>>>>>>>>>> 路由key: {}", envelope.getRoutingKey());
                log.info(">>>>>>>>>>>> 交换机：{}", envelope.getExchange());
                log.info(">>>>>>>>>>>> 消息id: {}", envelope.getDeliveryTag());
                log.info(">>>>>>>>>>>> consumer2接收的消息是：{}", new String(body));
            }
        };

        channel.basicConsume(Producer.TOPIC_QUEUE_2, true, consumer);
    }
}

package com.example.mq.simple;

import com.example.mq.ConnectionUtil;
import com.rabbitmq.client.*;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
public class Consumer {

    private static final String QUEUE_NAME = "simple_queue";

    public static void main(String[] args) throws Exception {

        Connection connection = ConnectionUtil.getConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(QUEUE_NAME, true, false, false, null);

        DefaultConsumer consumer = new DefaultConsumer(channel) {
            /**
             *
             * @param consumerTag 消费者标签
             * @param envelope 消息包内容，可以获取消息id，消息routingkey，交换机，消息和重传标识
             * @param properties 属性信息
             * @param body 消息
             * @throws IOException
             */
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                log.info(">>>>>>>>>>>>> consumerTag: {}, \n 消息id: {}, \n 交换机：{},\n 路由key：{},\n body: {}",
                        consumerTag, envelope.getDeliveryTag(), envelope.getExchange(), envelope.getRoutingKey(), new String(body));
            }
        };

        channel.basicConsume(QUEUE_NAME, true, consumer);
    }
}

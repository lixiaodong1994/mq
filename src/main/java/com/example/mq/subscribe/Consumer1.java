package com.example.mq.subscribe;

import com.example.mq.ConnectionUtil;
import com.rabbitmq.client.*;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
public class Consumer1 {

    public static void main(String[] args) throws Exception {

        final Connection connection = ConnectionUtil.getConnection();
        final Channel channel = connection.createChannel();

        // 声明交换机
        channel.exchangeDeclare(Producer.FANOUT_EXCHANGE, BuiltinExchangeType.FANOUT);

        // 声明队列
        channel.queueDeclare(Producer.FANOUT_QUEUE_1, true, false, false, null);

        // 队列绑定交换机
        channel.queueBind(Producer.FANOUT_QUEUE_1, Producer.FANOUT_EXCHANGE, "");

        DefaultConsumer consumer = new DefaultConsumer(channel) {

            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                log.info(">>>>>>>>>>>> 路由key: {}", envelope.getRoutingKey());
                log.info(">>>>>>>>>>>> 交换机：{}", envelope.getExchange());
                log.info(">>>>>>>>>>>> 消息id: {}", envelope.getDeliveryTag());
                log.info(">>>>>>>>>>>> consumer1接收的消息：{}", new String(body));
            }
        };

        channel.basicConsume(Producer.FANOUT_QUEUE_1, true, consumer);
    }
}

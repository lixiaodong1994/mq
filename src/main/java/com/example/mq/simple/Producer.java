package com.example.mq.simple;

import com.example.mq.ConnectionUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;

@Slf4j
public class Producer {

    private static final String QUEUE_NAME = "simple_queue";

    public static void main(String[] args) throws Exception {

        Connection connection = ConnectionUtil.getConnection();
        Channel channel = connection.createChannel();

        /*
         * param1 queue the name of the queue
         * param2 durable true if we are declaring a durable queue (the queue will survive a server restart)
         * param3 exclusive true if we are declaring an exclusive queue (restricted to this connection)
         * param4 autoDelete true if we are declaring an autodelete queue (server will delete it when no longer in use)
         * param5 arguments other properties (construction arguments) for the queue
         */
        channel.queueDeclare(QUEUE_NAME, true, false, false, null);

        String message = "hello world!";

        channel.basicPublish("", QUEUE_NAME, null, message.getBytes(StandardCharsets.UTF_8));

        log.info(">>>>>>>>>>>> send message: {}", message);

        channel.close();
        connection.close();
    }
}

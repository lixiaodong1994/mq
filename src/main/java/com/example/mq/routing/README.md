# RabbitMQ - 路由模式

路由模式特点：

- 队列与交换机绑定，不能是任意绑定了，而且要指定一个routing key
- 向exchange发送消息时，也必须要指定routing key
- exchange不再把消息交给每一个绑定的队列，而是需要根据routing key进行判断，只有一致的routing key才会接收到消息

![img.png](img.png)

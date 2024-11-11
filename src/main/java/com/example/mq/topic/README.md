# RabbitMQ - 主题模式

Topic类型和Direct不同的是，Topic可以根据Routing key把消息路由到不同的队列。Topic类型的交换机可以在绑定Routing key的时候可以使用通配符。  
举例：

- '#': 匹配一个或多个词
- '*': 匹配不多不少一个词

![img.png](img.png)

RabbitMQ界面的Exchange交换机关系：
![img_2.png](img_2.png)
![img_1.png](img_1.png)
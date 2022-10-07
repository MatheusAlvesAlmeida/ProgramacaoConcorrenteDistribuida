package Consumer;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

public class Consumer {

  private static final String QUEUE_NAME = "queue";

  public static void main(String[] argv) throws Exception {
    ConnectionFactory factory   = new ConnectionFactory();    // constructs connection instances
    factory.setHost("localhost");

    final Connection connection = factory.newConnection();    // used to open a channel
    final Channel channel       = connection.createChannel(); // represents an AMQP 0-9-1 channel, and provides most of the operations (protocol methods).

    boolean durable = true;                                   // make sure that the queue will survive a RabbitMQ node restart
    channel.queueDeclare(QUEUE_NAME, durable, false, false, null);
    System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

    int prefetchCount = Integer.parseInt(argv[0]);
    channel.basicQos(prefetchCount);

    /*
        Callback interface to be notified when a message is delivered.
           consumerTag - the consumer tag associated with the consumer
           message - the delivered message
     */
    DeliverCallback deliverCallback = (consumerTag, delivery) -> {    
        String message = new String(delivery.getBody(), "UTF-8");

        System.out.println(" [x] Received");

        System.out.println(" [x] Ack Done");
        channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
    };

    boolean autoAck = false;
    channel.basicConsume(QUEUE_NAME, autoAck, deliverCallback, consumerTag -> { });
  }
}
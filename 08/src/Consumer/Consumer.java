package Consumer;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.DeliverCallback;
import com.rabbitmq.client.Envelope;
import java.io.*;

public class Consumer {

  private static final String QUEUE_NAME = "queue";

  public static void main(String[] argv) throws Exception {
    ConnectionFactory factory = new ConnectionFactory();
    factory.setHost("localhost");

    Connection connection = factory.newConnection();
    Channel channel = connection.createChannel();
    channel.basicQos(0);

    channel.queueDeclare(QUEUE_NAME, false, false, false, null);
    System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

    DeliverCallback deliverCallback = (consumerTag, delivery) -> {
      try {
        System.out.println("Tag: " + delivery.getEnvelope().getDeliveryTag());
        String message = new String(delivery.getBody(), "UTF-8");
      } finally {
        System.out.println(" [x] Received '" + message + "'");
        channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
      }
    };
    channel.basicConsume(
      QUEUE_NAME,
      true,
      deliverCallback,
      consumerTag -> {},
      new DefaultConsumer(channel) {
        public void handleDelivery(
          String consumerTag,
          Envelope envelope,
          AMQP.BasicProperties properties,
          byte[] body
        )
          throws IOException {
          long deliveryTag = envelope.getDeliveryTag();
          // positively acknowledge a single delivery, the message will
          // be discarded
          channel.basicAck(deliveryTag, false);
        }
      }
    );
  }
}

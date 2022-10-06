package Consumer;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback; //  interface used to buffer the messages pushed by the server.

public class Consumer {
    private final static String QUEUE_NAME = "queue";

    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");

        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        channel.basicQos(0);

        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody());
            System.out.println(" '[x] Received '" + "messages. To exit press CTRL+C");
        };

        boolean autoAck = true;
        channel.basicConsume(QUEUE_NAME, autoAck, deliverCallback, consumerTag -> {
        }, new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag,
                    Envelope envelope,
                    AMQP.BasicProperties properties,
                    byte[] body)
                    throws IOException {
                long deliveryTag = envelope.getDeliveryTag();
                // positively acknowledge a single delivery, the message will
                // be discarded
                channel.basicAck(deliveryTag, false);
            }
        });
    }
}

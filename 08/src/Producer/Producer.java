package Producer;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

public class Producer {

  private static final String QUEUE_NAME = "queue";

  public static String getDatetime() {
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
    LocalDateTime now = LocalDateTime.now();
    return dtf.format(now);
  }

  public static void main(String[] argv) throws Exception {
    ConnectionFactory factory = new ConnectionFactory();
    factory.setHost("localhost");
    
    try (Connection connection = factory.newConnection(); Channel channel = connection.createChannel()) {
        boolean durable = true;                     // make sure that the queue will survive a RabbitMQ node restart
        channel.queueDeclare(QUEUE_NAME, durable, false, false, null);

        byte[] message = new byte[1024];
        String newMessage = "Producer " + argv[0] + ": #"
                + Producer.getDatetime();
        //fill newMessage with 1024 bytes
        for (int i = newMessage.length(); i < 1024; i++) {
          message[i] = ' ';
        }
        message = newMessage.getBytes();
        /* 
           ""                                      : indicates the use of a default exchange
           MessageProperties.PERSISTENT_TEXT_PLAIN : mark our messages as persistent, telling RabbitMQ to save the message to disk
         */
        for (int i = 0; i < 10000; i++) {
            channel.basicPublish("", QUEUE_NAME, MessageProperties.PERSISTENT_TEXT_PLAIN, message);
            System.out.println(" [x] Sent '" + message + "'");
        }
    }
  }
}
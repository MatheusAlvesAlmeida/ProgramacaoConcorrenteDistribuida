package Consumer;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.io.File;
import java.io.IOException;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

public class Consumer {

  private static final String QUEUE_NAME = "queue";

  public static String convertLocalDateTimeToCurrentTimeMillis(String date) {
    // get current date time with LocalDateTime.now() and format it
    LocalDateTime now = LocalDateTime.now();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
    String formatDateTime = now.format(formatter);
    
    // convert it to milliseconds
    long millis = now.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    // subtract format date time from current date time
    long diff = millis
            - LocalDateTime.parse(date, formatter).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    return String.valueOf(diff);
  }

  public static void main(String[] argv) throws Exception {
    try {
      File myObj = new File("pc0.txt");

      if (myObj.createNewFile())
          System.out.println("File created: " + myObj.getName());

      else
          System.out.println("File already exists.");

    } catch (IOException e) {
        System.out.println("An error occurred.");
        e.printStackTrace();
    }
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

        String producerDateTime = message.split("#")[1];
        String diff = convertLocalDateTimeToCurrentTimeMillis(producerDateTime);

        try {
          FileWriter fw = new FileWriter("pc0.txt", true);
          BufferedWriter bw = new BufferedWriter(fw);
          bw.write(diff);
          bw.newLine();
          bw.close();
        } catch (IOException e) {
          System.out.println("An error occurred.");
          e.printStackTrace();
        }
        System.out.println(" [x] Received");

        System.out.println(" [x] Ack Done");
        channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
    };

    boolean autoAck = false;
    channel.basicConsume(QUEUE_NAME, autoAck, deliverCallback, consumerTag -> { });
  }
}

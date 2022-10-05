package Consumer;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.io.File;
import java.io.IOException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback; //  interface used to buffer the messages pushed by the server.

public class Consumer {
    private final static String QUEUE_NAME = "queue";

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
            File myObj = new File("consumer5096.txt");

            if (myObj.createNewFile())
                System.out.println("File created: " + myObj.getName());

            else
                System.out.println("File already exists.");

        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");

        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody());
            String producerDateTime = message.split("#")[1];
            String diff = convertLocalDateTimeToCurrentTimeMillis(producerDateTime);

            try {
                FileWriter fw = new FileWriter("consumer5096.txt", true);
                BufferedWriter bw = new BufferedWriter(fw);
                bw.write(diff);
                bw.newLine();
                bw.close();
            } catch (IOException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
            }
            System.out.println(" [x] Received '" + message + "'");
        };
        channel.basicConsume(QUEUE_NAME, true, deliverCallback, consumerTag -> {
        });

    }

}

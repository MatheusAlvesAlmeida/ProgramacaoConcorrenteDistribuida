package Producer;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class Producer {
    private static final String QUEUE_NAME = "queue";
    private static int bytesQtd = 5096;

    public static String getDatetime() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        return dtf.format(now);
    }

    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");

        byte[] message = new byte[bytesQtd];
        String newMessage = "Producer " + argv[0] + ": #"
                + Producer.getDatetime();
        message = newMessage.getBytes();
        try (Connection connection = factory.newConnection(); // abstracts the socket connection
                Channel channel = connection.createChannel()) {

            channel.queueDeclare(QUEUE_NAME, false, false, false, null);

            for (int i = 0; i < 10000; i++) {
                channel.basicPublish("", QUEUE_NAME, null, message); // uses a default exchange, identified
                System.out.println(" [x] Sent '" + message + "'");
            }
        }
    }
}

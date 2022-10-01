package Producer;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;   


public class Producer {
    private static final String QUEUE_NAME = "queue";

    public static void main(String[] argv) throws Exception {

        ConnectionFactory factory = new ConnectionFactory(); 
        factory.setHost("localhost");

        try (Connection connection = factory.newConnection(); // abstracts the socket connection
             Channel channel = connection.createChannel()) {

                channel.queueDeclare(QUEUE_NAME, false, false, false, null);
                
                for(int i = 0; i < 10000; i++){
                    String message = "Producer " + argv[0] + " message number " + Integer.toString(i);
                    channel.basicPublish("", QUEUE_NAME, null, message.getBytes()); // uses a default exchange, identified by the empty string ("")
                    System.out.println(" [x] Sent '" + message + "'");
                }                
        }
    }
}

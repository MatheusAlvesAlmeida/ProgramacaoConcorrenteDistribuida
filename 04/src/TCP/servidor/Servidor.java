package TCP.servidor;

import java.net.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.io.*;

public class Servidor {
    public static void main(String args[]) {
        int port = 28887;
        try {
            ServerSocket server = new ServerSocket(port);
            System.out.println("Server started");
            System.out.println("Waiting for a client ...");

            while (true) {
                Socket socket = server.accept();
                System.out.println("Client accepted");

                Thread thread = new Thread(new Task(socket));
                thread.run();
            }

        } catch (IOException i) {
            System.out.println(i);
        }
    }
}

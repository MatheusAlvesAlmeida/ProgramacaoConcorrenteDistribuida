package TCP.cliente;

import java.net.*;
import java.io.*;

public class Cliente {
    public static void main(String args[]) throws IOException {
        int port = 28887;

        for (int i = 0; i < 10000; i++) {
            Socket socket = new Socket("localhost", port);
            System.out.println("Getting data...");

            DataInputStream input = new DataInputStream(socket.getInputStream());
            System.out.println(input.readUTF());

            socket.close();
            input.close();
        }
    }
}

package Cliente;

import java.net.*;
import java.io.*;

public class Cliente {
    private Socket socket = null;
    private DataInputStream input = null;
    private DataOutputStream output = null;

    public Cliente(String address, int port) {
        try {
            socket = new Socket(address, port);
            System.out.println("Connected");
            input = new DataInputStream(System.in);
            output = new DataOutputStream(socket.getOutputStream());
        } catch (UnknownHostException u) {
            System.out.println(u);
        } catch (IOException i) {
            System.out.println(i);
        }

        String line = "";

        while (!line.equals("Over")) {
            try {
                line = input.readLine();
                output.writeUTF(line);
            } catch (IOException i) {
                System.out.println(i);
            }
        }

        try {
            input.close();
            output.close();
            socket.close();
        } catch (IOException i) {
            System.out.println(i);
        }
    }

    public static void main(String args[]) {
        Cliente client = new Cliente("127.0.0.1", 5000);
    }
}

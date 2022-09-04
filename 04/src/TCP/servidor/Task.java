package TCP.servidor;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.Socket;
import java.net.SocketException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Task implements Runnable {
    byte[] enviarDados;
    DatagramPacket enviarPacote;
    Socket serverSocket;

    public Task(Socket serverSocket) throws SocketException {
        this.serverSocket = serverSocket;
    }

    @Override
    public void run() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();

        DataOutputStream output;
        try {
            output = new DataOutputStream(serverSocket.getOutputStream());
            output.writeUTF(dtf.format(now));

        } catch (IOException e) {
            e.printStackTrace();
        }

        // System.out.println("Data sent to client");
    }
}
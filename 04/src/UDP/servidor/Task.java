package UDP.servidor;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Task implements Runnable {
    Address clienteInfo;
    byte[] enviarDados;
    DatagramPacket enviarPacote;
    DatagramSocket serverSocket;

    public Task(Address clienteInfo, DatagramSocket serverSocket) throws SocketException {
        this.clienteInfo = clienteInfo;
        this.serverSocket = serverSocket;
    }

    @Override
    public void run() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();

        enviarDados = (dtf.format(now)).getBytes();
        enviarPacote = new DatagramPacket(enviarDados, enviarDados.length, clienteInfo.ip, clienteInfo.porta);

        try {
            serverSocket.send(enviarPacote);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
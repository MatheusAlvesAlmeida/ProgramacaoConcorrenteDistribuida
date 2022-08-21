package UDP.cliente;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Cliente {
    public static void main(String[] args) throws IOException {
        int porta = 28886;
        String endereço = "localhost";

        for (int i = 0; i < 10000; i++) {
            System.out.println("Teste " + i);

            DatagramSocket clientSocket = new DatagramSocket();
            InetAddress ipServidor = InetAddress.getByName(endereço);
            DatagramPacket enviarPacote, receberPacote;

            byte[] enviarDados;
            byte[] receberDados = new byte[64];

            enviarDados = ("Get timestamp").getBytes();
            enviarPacote = new DatagramPacket(enviarDados, enviarDados.length, ipServidor, porta);
            clientSocket.send(enviarPacote);

            receberPacote = new DatagramPacket(receberDados, receberDados.length);
            clientSocket.receive(receberPacote);
            System.out.println(new String(receberPacote.getData()));
        }
    }
}
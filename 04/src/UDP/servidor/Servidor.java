package UDP.servidor;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.Arrays;

public class Servidor {
        public static void main(String[] args) throws IOException, InterruptedException {
                int porta = 28886;

                DatagramSocket serverSocket = new DatagramSocket(porta);
                DatagramPacket receberPacote;
                byte[] receberDados = new byte[64];

                System.out.println("Servidor iniciado na porta " + porta);

                while (true) {
                        receberPacote = new DatagramPacket(receberDados, receberDados.length);
                        serverSocket.receive(receberPacote);

                        Address clienteInfo = new Address(receberPacote.getAddress(), receberPacote.getPort());
                        System.out.println("Enviando para o cliente: " + clienteInfo.porta + " " + clienteInfo.ip);

                        Task thread = new Task(clienteInfo, serverSocket);
                        thread.run();

                        Arrays.fill(receberDados, (byte) 0);
                }
        }
}
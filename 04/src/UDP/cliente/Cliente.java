package UDP.cliente;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Cliente {
    public static void main(String[] args) throws IOException {
        int porta = 28886;
        String endereço = "localhost";
        double[] rtt = new double[10000];
        double tempoInicio = 0, tempoFim = 0, soma = 0, media = 0, somaDesvioPadrao = 0;

        for (int i = 0; i < 10000; i++) {
            tempoInicio = System.currentTimeMillis();
            // System.out.println("Teste " + i);

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
            // System.out.println(new String(receberPacote.getData()));
            tempoFim = System.currentTimeMillis();
            rtt[i] = tempoFim - tempoInicio;
        }

        for (int i = 0; i < 10000; i++) {
            soma += rtt[i];
        }
        media = soma / 10000;
        System.out.println("Tempo médio de RTT: " + media + "ms");

        for (int i = 0; i < 10000; i++) {
            somaDesvioPadrao = somaDesvioPadrao + Math.pow((rtt[i] - soma), 2);

        }
        double mediaDP = somaDesvioPadrao / 10000;
        System.out.printf("Desvio padrão: %.3f\n", Math.sqrt(mediaDP));
    }
}
package TCP.cliente;

import java.net.*;
import java.io.*;

public class Cliente {
    public static void main(String args[]) throws IOException {
        int port = 28887;

        double[] rtt = new double[10000];
        double tempoInicio = 0, tempoFim = 0, soma = 0, media = 0, somaDesvioPadrao = 0;

        for (int i = 0; i < 10000; i++) {
            tempoInicio = System.currentTimeMillis();
            Socket socket = new Socket("localhost", port);

            System.out.println("Getting data...");

            DataInputStream input = new DataInputStream(socket.getInputStream());
            System.out.println(input.readUTF());

            socket.close();
            input.close();
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

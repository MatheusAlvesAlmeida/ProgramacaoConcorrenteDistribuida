package main;

import consumidor.Cliente;
import produtor.Barbeiro;

public class Main {
    public static void main(String[] args) throws Exception {
        Recepcao recepcao = new Recepcao(4);

        // Create 100 clients
        for (int i = 0; i < 15; i++)
            new Cliente(recepcao).start();

        // Create 1 barber
        new Barbeiro(recepcao).start();
    }
}

package client;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import clock.Datetime;

public class Client {
    private Client() {
    }

    public static void main(String[] args) {
        double tempoInicio = 0, tempoFim = 0, tempoTotal = 0;
        // Create a new text file
        try {
            File myObj = new File("RTTs_80.txt");
            if (myObj.createNewFile()) {
                System.out.println("File created: " + myObj.getName());
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        try {
            Registry registry = LocateRegistry.getRegistry(8888);
            for (int i = 0; i < 10000; i++) {
                tempoInicio = System.currentTimeMillis();
                Datetime stub = (Datetime) registry.lookup("Time now: ");
                String response = stub.getDatetime();
                System.out.println("Response: " + response);
                tempoFim = System.currentTimeMillis();
                tempoTotal = tempoFim - tempoInicio;
                System.out.println("Tempo de RTT: " + tempoTotal + "ms");
                try {
                    FileWriter fw = new FileWriter("RTTs_80.txt", true);
                    BufferedWriter bw = new BufferedWriter(fw);
                    bw.write("" + tempoTotal);
                    bw.newLine();
                    bw.close();
                    // System.out.println("Successfully wrote to the file.");
                } catch (IOException e) {
                    System.out.println("An error occurred.");
                    e.printStackTrace();
                }

            }
        } catch (Exception e) {
            System.err.println("Client exception: " + e.toString());
            e.printStackTrace();
        }
    }
}

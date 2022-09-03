package client;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import clock.Datetime;

public class Client {
    private Client() {
    }

    public static void main(String[] args) {
        try {
            Registry registry = LocateRegistry.getRegistry(8888);
            for (int i = 0; i < 10000; i++) {
                Datetime stub = (Datetime) registry.lookup("Time now: ");
                String response = stub.getDatetime();
                System.out.println("Response: " + response);
            }
        } catch (Exception e) {
            System.err.println("Client exception: " + e.toString());
            e.printStackTrace();
        }
    }
}

package client;

import interfaces.Table;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Client {

  private Client() {}

  public static void main(String[] args) {
    try {
      Registry registry = LocateRegistry.getRegistry(8888);
      Table stub = (Table) registry.lookup("Table");
      for (int i = 0; i < 10; i++) {
        String response = stub.sit("Cliente " + i);
        System.out.println("response: " + response);
      }
    } catch (Exception e) {
      System.err.println("Client exception: " + e.toString());
      e.printStackTrace();
    }
  }
}

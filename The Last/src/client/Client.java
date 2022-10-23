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
      String response = stub.sit("Cliente 1");
      System.out.println("response: " + response);
    } catch (Exception e) {
      System.err.println("Client exception: " + e.toString());
      e.printStackTrace();
    }
  }
}

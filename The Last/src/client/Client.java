package client;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Queue;

public class Client {

  private Client() {}

  public static void main(String[] args) {
    try {
      Registry registry = LocateRegistry.getRegistry(8888);
      Queue stub = (Queue) registry.lookup("Queue");
    } catch (Exception e) {
      System.err.println("Client exception: " + e.toString());
      e.printStackTrace();
    }
  }
}

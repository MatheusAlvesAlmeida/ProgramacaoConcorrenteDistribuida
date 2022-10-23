package server;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import queue.Queue;

public class Server implements Queue {

  private String[] queue = new String[5];

  public Server() {}

  public static void main(String args[]) {
    try {
      Server obj = new Server();
      Queue stub = (Queue) UnicastRemoteObject.exportObject(obj, 0);
      Registry registry = LocateRegistry.createRegistry(8888);
      registry.bind("Queue", stub);

      System.err.println("Servidor pronto");
    } catch (Exception e) {
      System.err.println("Server exception: " + e.toString());
      e.printStackTrace();
    }
  }

  @Override
  public Boolean isEmpty() throws RemoteException {
    return queue.length == 0;
  }

  @Override
  public void enqueue(String item) throws RemoteException {
    if (queue.length == 5) {
      System.out.println("Mesa cheia");
    } else {
      queue[queue.length] = item;
    }
  }

  @Override
  public String dequeue() throws RemoteException {
    if (queue.length == 0) {
      return "Mesa vazia";
    } else {
      String item = queue[0];
      for (int i = 0; i < queue.length - 1; i++) {
        queue[i] = queue[i + 1];
      }
      return item + " saiu da mesa";
    }
  }

  @Override
  public void printQueue() throws RemoteException {
    for (int i = 0; i < queue.length; i++) {
      System.out.println(queue[i]);
    }
  }
}

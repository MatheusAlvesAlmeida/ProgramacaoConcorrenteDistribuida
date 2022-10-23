package server;

import interfaces.Table;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class Server implements Table {

  private String[] table = new String[5];
  private String[] queue = new String[100];

  public Server() {}

  public static void main(String args[]) {
    try {
      Server obj = new Server();
      Table stub = (Table) UnicastRemoteObject.exportObject(obj, 0);
      Registry registry = LocateRegistry.createRegistry(8888);
      registry.bind("Table", stub);
      System.err.println("Servidor pronto");
    } catch (Exception e) {
      System.err.println("Server exception: " + e.toString());
      e.printStackTrace();
    }
  }

  @Override
  public String sit(String item) throws RemoteException {
    if (table.length == 5) {
      for (int i = 0; i < queue.length; i++) {
        if (queue[i] == null) {
          queue[i] = item;
          return "Mesa cheia - " + item + " na fila";
        }
      }
      return "Mesa cheia e fila cheia";
    } else {
      for (int i = 0; i < table.length; i++) {
        if (table[i] == null) {
          table[i] = item;
          return "Item adicionado";
        }
      }
    }
    return item;
  }

  @Override
  public String out() throws RemoteException {
    if (queue.length == 0) {
      return "Mesa vazia";
    } else {
      for (int i = 0; i < table.length; i++) {
        if (table[i] != null) {
          table[i] = null;
          // Get first item from queue and put it in table
          table[i] = queue[0];
          // Remove first item from queue
          for (int j = 0; j < queue.length; j++) {
            if (j == queue.length - 1) {
              queue[j] = null;
            } else {
              queue[j] = queue[j + 1];
            }
          }
          return "Item removido da mesa" + " e " + table[i] + " adicionado";
        }
      }
    }
    return null;
  }

  public void printQueue() throws RemoteException {
    for (int i = 0; i < queue.length; i++) {
      System.out.println(queue[i]);
    }
  }
}

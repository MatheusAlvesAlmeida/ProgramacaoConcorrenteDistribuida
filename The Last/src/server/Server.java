package server;

import interfaces.Table;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class Server implements Table {

  private String[] table;
  private String[] queue = new String[100];

  public Server() {}

  public static void main(String args[]) {
    try {
      Server obj = new Server();
      Table stub = (Table) UnicastRemoteObject.exportObject(obj, 0);
      Registry registry = LocateRegistry.createRegistry(8888);
      registry.bind("Table", stub);
      System.err.println("Servidor pronto");
      // Print table if it's not empty
      if (obj.table != null) {
        for (int i = 0; i < obj.table.length; i++) {
          System.out.println("Cadeira: " + obj.table[i]);
        }
      } else {
        System.out.println("Mesa vazia");
      }
    } catch (Exception e) {
      System.err.println("Server exception: " + e.toString());
      e.printStackTrace();
    }
  }

  @Override
  public String sit(String item) throws RemoteException {
    // If table is empty, create it
    if (this.table == null) {
      this.table = new String[5];
      // Add item to table
      for (int i = 0; i < this.table.length; i++) {
        if (this.table[i] == null) {
          this.table[i] = item;
          return "Sentado na cadeira " + i;
        }
      }
    }
    if (this.table[4] != null) {
      for (int i = 0; i < this.queue.length; i++) {
        if (this.queue[i] == null) {
          this.queue[i] = item;
          printTableAndQueue();
          return "Mesa cheia - " + item + " na fila";
        }
      }
      printTableAndQueue();
      return "Mesa cheia e fila cheia";
    } else {
      for (int i = 0; i < 5; i++) {
        if (this.table[i] == null) {
          this.table[i] = item;
          return "Sentado na cadeira " + i;
        }
      }
    }
    return item;
  }

  @Override
  public String out() throws RemoteException {
    if (this.queue.length == 0) {
      return "Mesa vazia";
    } else {
      for (int i = 0; i < table.length; i++) {
        if (this.table[i] != null) {
          this.table[i] = null;
          // Get first item from queue and put it in table
          this.table[i] = this.queue[0];
          // Remove first item from queue
          for (int j = 0; j < this.queue.length; j++) {
            if (j == this.queue.length - 1) {
              this.queue[j] = null;
            } else {
              this.queue[j] = this.queue[j + 1];
            }
          }
          printTableAndQueue();
          return (
            "Item removido da mesa" + " e " + this.table[i] + " adicionado"
          );
        }
      }
    }
    return null;
  }

  public void printTableAndQueue() {
    // Print table if it's not empty
    if (this.table != null) {
      for (int i = 0; i < this.table.length; i++) {
        System.out.println("Cadeira: " + this.table[i]);
      }
    } else {
      System.out.println("Mesa vazia");
    }
    // Print queue if it's not empty
    if (this.queue != null) {
      for (int i = 0; i < this.queue.length; i++) {
        System.out.println("Fila: " + this.queue[i]);
      }
    } else {
      System.out.println("Fila vazia");
    }
  }
}

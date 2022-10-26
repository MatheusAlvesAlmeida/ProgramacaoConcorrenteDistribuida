package server;

import interfaces.Table;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class Server implements Table {

  private String[] table;
  private String[] queue = new String[100];
  private boolean groupFormed = false;

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
      while (true) {
        Thread.sleep(5000);
        // Call remove random method if table is not empty
        if (obj.table != null) {
          obj.removeRandomItemFromTable();
        }
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
        if (this.table[i] == null && !this.groupFormed) {
          this.table[i] = item;
          printTableAndQueue();
          if (i == 4) {
            this.groupFormed = true;
          }
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

  public void printTableAndQueue() {
    // Print table if it's not empty
    if (this.table != null) {
      for (int i = 0; i < this.table.length; i++) {
        System.out.println("Cadeira: " + this.table[i]);
      }
    } else {
      System.out.println("Mesa vazia");
    }
    // Print quantity of items in queue
    int count = 0;
    for (int i = 0; i < this.queue.length; i++) {
      if (this.queue[i] != null) {
        count++;
      }
    }
    System.out.println(count + " itens na fila");
  }

  public void removeRandomItemFromTable() {
    // If is group formed, remove all items from table
    if (this.groupFormed) {
      for (int i = 0; i < this.table.length; i++) {
        this.table[i] = null;
      }
      this.groupFormed = false;
      System.out.println("Grupo saiu, preenchendo mesa...");
      printTableAndQueue();
      // If table is empty, add items from queue to table
      for (int i = 0; i < this.table.length; i++) {
        if (this.queue[i] != null) {
          this.table[i] = this.queue[i];
          this.queue[i] = null;
          System.out.println("Adicionando " + this.table[i] + " na mesa");
        }
      }
      printTableAndQueue();
    } else {
      // Remove random item from table
      int random = (int) (Math.random() * 5);
      if (this.table[random] != null) {
        System.out.println("Removendo " + this.table[random] + " da mesa");
        this.table[random] = null;
      }
      // Add item from queue to table
      for (int i = 0; i < this.table.length; i++) {
        if (this.table[i] == null && this.queue[i] != null) {
          this.table[i] = this.queue[i];
          this.queue[i] = null;
          System.out.println("Adicionando " + this.table[i] + " na mesa");
        }
      }
    }
  }
}

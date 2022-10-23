package queue;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Queue extends Remote {
  Boolean isEmpty() throws RemoteException;
  void enqueue(String item) throws RemoteException;
  String dequeue() throws RemoteException;
  void printQueue() throws RemoteException;
}

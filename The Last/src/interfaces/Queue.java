package interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Queue extends Remote {
  Boolean isEmpty() throws RemoteException;
  void printQueue() throws RemoteException;
}

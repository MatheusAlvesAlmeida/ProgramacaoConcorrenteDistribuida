package interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Table extends Remote {
  String sit(String item) throws RemoteException;
}

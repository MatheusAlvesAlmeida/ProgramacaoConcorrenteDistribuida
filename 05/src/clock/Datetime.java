package clock;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Datetime extends Remote {
    String getDatetime() throws RemoteException;
}

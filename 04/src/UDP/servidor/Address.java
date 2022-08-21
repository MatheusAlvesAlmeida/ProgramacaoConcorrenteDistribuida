package UDP.servidor;

import java.net.InetAddress;

public class Address {
    InetAddress ip;
    int porta;

    public Address(InetAddress ip, int porta) {
        this.ip = ip;
        this.porta = porta;
    }
}
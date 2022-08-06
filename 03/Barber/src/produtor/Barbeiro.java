package produtor;

import main.Buffer;

public class Barbeiro extends Thread {
    private Buffer buffer;

    public Barbeiro(Buffer buffer) {
        this.buffer = buffer;
    }

    @Override
    public void run() {
        this.buffer.cortaCabelo();
    }
}
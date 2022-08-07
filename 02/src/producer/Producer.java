package producer;

import main.Buffer;

public class Producer extends Thread {
    private Buffer buffer;
    private int randomNumber;

    public Producer(Buffer buffer) {
        this.buffer = buffer;
    }

    @Override
    public void run() {
        randomNumber = (int) (Math.random() * 100);
        this.buffer.produzir(randomNumber);
    }
}

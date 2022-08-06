package consumer;

import main.Buffer;

public class Consumer extends Thread {
    private Buffer buffer;

    public Consumer(Buffer buffer) {
        this.buffer = buffer;
    }

    @Override
    public void run() {
        int randomNumber;
        randomNumber = this.buffer.get();
        System.out.println(Thread.currentThread().getName() + " consumed: " + randomNumber);
    }
}

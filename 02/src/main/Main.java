package main;

import consumer.Consumer;
import producer.Producer;

public class Main {
    public static void main(String[] args) {
        Buffer buffer = new Buffer();

        Producer producer = new Producer(buffer);
        producer.start();

        for (int i = 0; i < 2; i++) {
            Consumer consumer = new Consumer(buffer);
            consumer.start();
        }

    }
}

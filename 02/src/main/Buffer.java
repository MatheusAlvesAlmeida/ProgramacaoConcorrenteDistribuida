package main;

import java.util.ArrayList;

public class Buffer {
    private ArrayList<Integer> contents = new ArrayList<Integer>();

    public synchronized int get() {
        while (this.contents.isEmpty()) {
            System.out.println("Consumer: " + Thread.currentThread().getName() + " is waiting for data");
            try {
                wait();
            } catch (InterruptedException e) {
                System.out.println("Consumer: " + Thread.currentThread().getName() + " was interrupted");
            }
        }

        int value = this.contents.remove(this.contents.size() - 1);
        System.out.println("Consumer: " + Thread.currentThread().getName() + " item consumed");
        return value;
    }

    public synchronized void put(int value) {
        while (!this.contents.isEmpty()) {
            System.out.println("Producer: " + Thread.currentThread().getName() + " is waiting for space");
            try {
                wait();
            } catch (InterruptedException e) {
                System.out.println("Producer: " + Thread.currentThread().getName() + " was interrupted");
            }
        }

        int threadCount = Thread.activeCount() - 2; // disconsider main thread and itself

        for (int i = 0; i < threadCount; i++)
            this.contents.add(value);

        System.out.println("Producer: " + Thread.currentThread().getName() + " item inserted");
        notifyAll();
    }
}

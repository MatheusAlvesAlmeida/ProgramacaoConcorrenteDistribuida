package main;

import java.util.ArrayList;

public class Recepcao {
    private int numberChairs;
    private ArrayList<String> chairs;

    // CONDITIONS
    // 1 - is full and the customer leaves
    // 2 - the client wakes up the barber
    // 3 - the barber is busy and the client goes to the waiting room
    // 4 - there are no customers the barber sleeps

    public Recepcao(int numberChairs) {
        this.numberChairs = numberChairs;
        this.chairs = new ArrayList<String>(this.numberChairs);
    }

    public synchronized int get() throws InterruptedException {
        while (true) {
            while (this.chairs.isEmpty()) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            String removedThread = this.chairs.remove(this.chairs.size() - 1);
            System.out.println("Barber: " + removedThread + " cut hair");
            Thread.sleep(1);
        }
    }

    // method to add a client to the waiting room
    public synchronized void put(String threadName) throws InterruptedException {
        if (this.chairs.size() < this.numberChairs) {
            this.chairs.add(threadName);
            System.out.println("Barber: " + threadName + " is waiting");
            notify();
            Thread.sleep(1);
        }

        else
            System.out.println("Barber: " + threadName + " left");

    }
}
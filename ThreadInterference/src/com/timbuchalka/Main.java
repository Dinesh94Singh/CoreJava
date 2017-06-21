package com.timbuchalka;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.timbuchalka.Main.EOF;

public class Main {
    public static final String EOF = "EOF";

    public static void main(String[] args) {
        // Shared variable buffer
        List<String> buffer = new ArrayList<String>();
        MyProducer producer = new MyProducer(buffer, ThreadColor.ANSI_YELLOW);
        MyConsumer consumer1 = new MyConsumer(buffer, ThreadColor.ANSI_PURPLE);
        MyConsumer consumer2 = new MyConsumer(buffer, ThreadColor.ANSI_CYAN);

        new Thread(producer).start(); // Runnable Interface implemented for Producer and Consumer
        new Thread(consumer1).start();
        new Thread(consumer2).start();
    }
}

class MyProducer implements  Runnable {
    private List<String> buffer;
    private String color;

    public MyProducer(List<String> buffer, String color) {
        this.buffer = buffer;
        this.color = color;
    }

    public void run() {
        Random random = new Random();
        String[] nums = { "1", "2", "3", "4", "5"};

        for(String num: nums) {
            try {
                System.out.println(color + "Adding..." + num);

                // with out synchronized it is interleaved and can be suspended in between.
                // if I put 'this' instead. What is the Problem ?
                // Answer: If I put this here, since we have two threads belong to two different classes
                // How would the synchronization occur. If we have 2 threads for same instance then
                // "this" would be appropriate. But since 2 threads from 2 different Classes
                // having a common variable in Buffer makes us to use a lock on that common variable
                // synchronized is making us to have a lock on buffer.
                synchronized(buffer) {
                    buffer.add(num); // added to buffer
                }
                // Once added, lock is unlocked and thread is going to sleep.
                // At this time, Chance is given for Consumers to consume

                Thread.sleep(random.nextInt(1000)); // from 0 - 1000 it will sleep
            } catch(InterruptedException e) {
                System.out.println("Producer was interrupted");
            }
        }

        System.out.println(color + "Adding EOF and exiting....");
        synchronized(buffer) {
            buffer.add("EOF"); // since all numbers are produced. Specify EOF
        }
    }
}

class MyConsumer implements Runnable {
    private List<String> buffer;
    private String color;

    public MyConsumer(List<String> buffer, String color) {
        this.buffer = buffer;
        this.color = color;
    }

    public void run() {
        while(true) {
            // with out synchronized it is interleaved and can be suspended in between.
            // 2 threads have been on Consumer. You might ask why didn't i Use "this"
            // ANSWER: Because, buffer is still being used in Producer even though we have 2 threads from Consumer
            //
            synchronized(buffer) {
                // if I put 'this' instead. It will not release the lock until exception occurs
                if(buffer.isEmpty()) {
                    continue;
                }
                if(buffer.get(0).equals(EOF)) {
                    System.out.println(color + "Exiting");
                    break;
                } else {
                    System.out.println(color + "Removed " + buffer.remove(0));
                }
            } // Once either of 2 consumers are awaken, It wil consume
            // Once consumed it will stay in loop. until buffer is again not empty.
            // When Buffer is not empty. there is a race condition between 2 threads
        }
    }
}

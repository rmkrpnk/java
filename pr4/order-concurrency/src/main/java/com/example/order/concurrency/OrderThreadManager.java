package com.example.order.concurrency;

import java.util.ArrayList;
import java.util.List;

public abstract class OrderThreadManager {

    public static void runAll(Runnable... tasks) throws InterruptedException {
        List<Thread> threads = new ArrayList<>();
        for (Runnable task : tasks) {
            Thread thread = new Thread(task);
            thread.start();
            threads.add(thread);
        }
        for (Thread thread : threads) {
            thread.join();
        }
    }
}

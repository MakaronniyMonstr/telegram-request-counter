package org.example;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        ScheduledExecutorService service1 = Executors.newSingleThreadScheduledExecutor(r -> {
            System.out.println("Thread created");
            return new Thread(r);
        });
        service1.schedule(() -> System.out.println(1), 100, TimeUnit.MILLISECONDS);
        service1.schedule(() -> System.out.println(1), 100, TimeUnit.MILLISECONDS);
        service1.awaitTermination(1, TimeUnit.DAYS);
    }
}
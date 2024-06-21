package com.behrad.miscellaneous.concurrency;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ThreadPool extends Thread {

    public static void main(String[] args) {
        List<AbstractMap.SimpleEntry<Integer, Future<Double>>> list = new ArrayList<>();
        ExecutorService executorThreadPool = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 10; i++) {
            int finalI = i;
            Future<Double> submit = executorThreadPool.submit(() -> {
                Thread.sleep(5000);
                return Math.pow(finalI, 2);
            });
            list.add(new AbstractMap.SimpleEntry<>(finalI, submit));
        }
        int count = 0;
        int step = 0;
        while (list.size() != count) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            if (step >= list.size()) {
                step = 0;
                System.out.println("ALL LOOPED");
            }
            AbstractMap.SimpleEntry<Integer, Future<Double>> e = list.get(step++);
            if (e.getValue().isDone()) {
                try {
                    System.out.format("%d to the power of 2 is %.2f\n", e.getKey(), e.getValue().get());
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
                count++;
            }
        }
        System.out.println("job done");
    }
}

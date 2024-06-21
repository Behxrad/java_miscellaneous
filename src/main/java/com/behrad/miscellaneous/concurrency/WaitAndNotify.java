package com.behrad.miscellaneous.concurrency;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

public class WaitAndNotify {

    private Object publish = new Object();

    private BlockingDeque<Message> messages = new LinkedBlockingDeque<>();

    public static void main(String[] args) {
        WaitAndNotify waitAndNotify = new WaitAndNotify();

        new Thread(() -> {
            String[] arr = new String[]{"agkajlk;sadf", "jklfdagkl;das", "jiuwhfvkjnjkhjs"};
            for (String s : arr) {
                waitAndNotify.send(s);
            }
        }).start();

        new Thread(() -> waitAndNotify.receive()).start();
    }

    public static class Message {

        private String message;

        public Message(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }

    public void send(String m) {
        try {
            Message msg = new Message(m);
            synchronized (msg) {
                messages.add(msg);
                System.out.println(msg.toString() + " sent");
                Thread.sleep(5000);
                msg.wait();
                System.out.println(msg.toString() + " read");
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }

    public void receive() {
        try {
            while (true) {
                Message msg = messages.take();
                synchronized (msg) {
                    msg.notify();
                    System.out.println(msg.toString() + " received");
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

package org.example.concurent;

import java.util.concurrent.TimeUnit;

public class Test1 {
    public static void main(String[] args) throws InterruptedException {
        t1 t1 = new t1();
        new Thread(()->{
            try {
                t1.sendsms();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"A").start();

      //  TimeUnit.SECONDS.sleep(1);

        new Thread(()->{ t1.call();},"B").start();
    }
}
class t1{
    public synchronized void sendsms() throws InterruptedException {
        TimeUnit.SECONDS.sleep(3);
        System.out.println("发短信");
    }
    public synchronized void call() {
        System.out.println("打电话");
    }
}
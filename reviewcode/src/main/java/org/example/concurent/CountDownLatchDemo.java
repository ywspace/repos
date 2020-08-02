package org.example.concurent;

import java.util.concurrent.CountDownLatch;

public class CountDownLatchDemo {

    static final  int count=6;

    public static void main(String[] args) throws InterruptedException {
        CountDownLatch countDownLatch=new CountDownLatch(count);

        for (int i = 0; i < 6; i++) {
            new Thread(()->{

                System.out.println(Thread.currentThread().getName()+" 同学离开自习室！");

                countDownLatch.countDown();

            },String.valueOf(i)).start();
        }

        countDownLatch.await();

        System.out.println("全员离开，值班人关门！");
    }
}

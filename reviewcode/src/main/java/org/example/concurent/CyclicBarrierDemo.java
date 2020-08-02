package org.example.concurent;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * CyclicBarrier 循环屏蔽
 */
public class CyclicBarrierDemo {
    public static void main(String[] args) {
        CyclicBarrier cyclicBarrier=new CyclicBarrier(7,()->{
            System.out.println("*****召唤神龙****");
        });

        for (int i = 1; i < 8; i++) {
            final int tempInt=i;
            new Thread(()->{

                System.out.println(Thread.currentThread().getName()+" 收集到第："+tempInt+" 颗龙珠");

                try {
                    cyclicBarrier.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }

            },String.valueOf(i)).start();
        }

    }
}

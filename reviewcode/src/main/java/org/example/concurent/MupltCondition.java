package org.example.concurent;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 题目： 多线程 之间 顺序调用  实现 A-> B -> C 三个线程启动 要求如下：
 * AA 打印5次 BB 打印10次 CC 打印15
 * 紧接着
 * AA 打印5次 BB 打印10次 CC 打印15
 * .......
 * 来10轮
 */
public class MupltCondition {

    public static void main(String[] args) {

        ShareResouce shareResouce =new ShareResouce();
        
        new Thread(()->{

            for (int i =1; i <=10; i++) {
                shareResouce.print5();
            }
            
        },"A").start();
        new Thread(()->{

            for (int i = 1; i <= 10; i++) {
                shareResouce.print10();
            }

        },"B").start();
        new Thread(()->{

            for (int i = 1; i <=10; i++) {
                shareResouce.print15();
            }

        },"C").start();

    }

}

class ShareResouce {
    //A：1 、 B：2 、C：3
    private int number = 1;
    private Lock lock = new ReentrantLock();
    private Condition c1 = lock.newCondition();
    private Condition c2 = lock.newCondition();
    private Condition c3 = lock.newCondition();

    public void print5() {
        lock.lock();
        try {
            // 1 判断
            while (number != 1) {
                c1.await();
            }
            // 2 干活
            for (int i = 0; i < 5; i++) {
                System.out.println(Thread.currentThread().getName() + " " + i);
            }
            // 3 修改标志   通知
            number = 2;
            c2.signal();
        } catch (Exception exception) {
            exception.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void print10() {
        lock.lock();

        try {
            // 1 判断
            while (number != 2) {
                c2.await();
            }
            // 2 干活
            for (int i = 0; i < 10; i++) {
                System.out.println(Thread.currentThread().getName() + " " + i);
            }
            // 3 修改标志   通知
            number = 3;
            c3.signal();
        } catch (Exception exception) {
            exception.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
    public void print15() {
        lock.lock();

        try {
            // 1 判断
            while (number != 3) {
                c3.await();
            }
            // 2 干活
            for (int i = 0; i < 15; i++) {
                System.out.println(Thread.currentThread().getName() + " " + i);
            }
            // 3 修改标志   通知
            number = 1;
            c1.signal();
        } catch (Exception exception) {
            exception.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

}

package org.example.concurent;

import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 可重入锁（也叫递归锁） 最大作用是避免死锁。
 * ReentrantLock / Synchronized 就是一个典型的可重入锁 (eg:如下)
 *
 * 指的是同一线程 外层函数获得锁之后，内层递归函数 仍然可以获取该锁代码
 *
 * 在一个线程的外层方法获取锁的时候，在进入内层方法会自动获取锁
 * 也即是说，线程可以进入任何一个它已经拥有锁所同步着的代码块。
 * tt0 sendMsg
 * tt0 sendEmail
 * tt4 sendMsg
 * tt4 sendEmail
 * tt3 sendMsg
 * tt3 sendEmail
 *
 *
 * synchronized 和lock  有什么区别？用新的lock 有什么好处？举例说明
 *   a、原始构成
 *        synchronized 是关键字 属于 JVM 层面， Lock 是具体类（java.util.concurrent.locks.Lock） 是api 层面的锁。
 *   b、使用方法
 *        synchronized 不需要用户手动去释放锁，当 synchronized 代码执行完成后系统会自动让线程释放对锁的占用。
 * 	   ReentrantLock 则需要用户手动释放锁，若没主动释放锁，就有可能导致死锁现象出现。需要lock() 和 unlock() 方法配合try/finally 语块来完成。
 *   c、等待是否中断
 *       synchronized 不可中断，除非抛出异常或者 正常运行完成。
 * 	  ReentrantLock 可中断， 1.设置超时方法，trylock(long timeout , TimeUnit unit)  2. lockInterruptibly() 放代码块中，调用interrupt() 方法可中断。
 *   d、是否公平锁
 *       synchronized 是非公平锁。
 * 	  ReentrantLock 两者都可以，默认是非公平锁，构造方法可以传boolean值 ,true 为公平锁，false 非公平锁。
 *   e、锁绑定多个条件 Condition
 *       synchronized 没有
 * 	  ReentrantLock 用来实现 分组 唤醒需要唤醒的线程们，可以精确唤醒，而不是像 synchronized 要么随机唤醒一个线程，要么唤醒全部线程。
 *
 */
public class LockDemo implements Runnable {

    ReentrantLock lock=new ReentrantLock();


    public synchronized void sendMsg() {
        System.out.println(Thread.currentThread().getName()+" sendMsg ");
        sendEmail();
    }
    public synchronized void sendEmail(){
        System.out.println(Thread.currentThread().getName()+" sendEmail");
    }

    public static void main(String[] args) {
        LockDemo lockDemo = new LockDemo();
        for (int i=0;i<3;i++) {
            new Thread(() -> {
                lockDemo.sendMsg();
            }, "tt"+i).start();
        }
        lockDemo.sendMsg();
        //等待线程执行完成
        while (Thread.activeCount()>2){
            Thread.yield();
        }

        System.out.println("=========================");

        Thread t1=new Thread(lockDemo,"mm1");
        Thread t2=new Thread(lockDemo,"mm2");
        t1.start();
        t2.start();
    }

    @Override
    public void run() {
      get();
    }

    public void get(){
        lock.lock(); //lock() 和 unlock() 要成对出现，否则 编译没问题，执行没问题， 但是程序会被卡住。
        lock.lock();
        try {
            System.out.println(Thread.currentThread().getName()+" get");
            set();
        } finally {
            lock.unlock();
            lock.unlock();
        }
    }
    public void set(){
        lock.lock();
        try {
            System.out.println(Thread.currentThread().getName()+" set");

        } finally {
            lock.unlock();
        }
    }
}

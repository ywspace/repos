package org.example.block;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 传统方式 演示  生产 一个 消费一个 的过程。
 * //【多线程的判断 必须用 while,否则两个线程没问题，多个线程 就会出错。控制不住】
 */
public class TransBlockingDemo {
    public static void main(String[] args) {
        Meshie meshie=new Meshie();

        for (int i = 0; i < 5; i++) {
            new Thread(()->{
                meshie.increamte();
            },"AAA").start();
        }

        for (int i = 0; i < 5; i++) {
            new Thread(()->{
                meshie.decreamte();
            },"BBB").start();
        }

    }
}

/**
 * 传统 多线程交互   加一减一
 */
class  Meshie
{
    private Integer number=0;
    private ReentrantLock lock=new ReentrantLock();
    private  Condition condition = lock.newCondition();

    /**
     *   方法作用 ： number  递增一
     */
    public   void increamte(){

        lock.lock();

        try {
            //【多线程的判断 必须用 while,否则两个线程没问题，多个线程 就会出错。控制不住】
            while (number!=0){
                // 当条件不符  线程等待
                condition.await();
            }
            //执行业务递增
            number++;

            System.out.println(Thread.currentThread().getName() +" "+number);
           //唤醒
           condition.signalAll();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }

    }
    /**
     *   方法作用 ： number  递减一
     */
    public void decreamte(){
       lock.lock();
        try {

            while (number==0){
                // 当条件不符  线程等待
                condition.await();
            }
            //执行业务递增
            number--;
            System.out.println(Thread.currentThread().getName() +" "+number);
            //唤醒
            condition.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}

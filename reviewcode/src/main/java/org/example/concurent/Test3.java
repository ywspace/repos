package org.example.concurent;

import java.util.concurrent.TimeUnit;

public class Test3 {
    public static void main(String[] args) throws InterruptedException {
        T3 t3 = new T3();
        T3 t5 = new T3();
        new Thread(()->{
            try {
                t3.sendsms();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"A").start();

        TimeUnit.SECONDS.sleep(1);

        new Thread(()->{t3.call();},"B").start();
    }
}
class T3{
    //synchronized锁的对象是方法的调用者（单个new的对象）
    //使用static关键字
    public static synchronized void sendsms() throws InterruptedException {
        TimeUnit.SECONDS.sleep(3);
        System.out.println("发短信");
    }
    public static synchronized void call() {
        System.out.println("打电话");
    }
}
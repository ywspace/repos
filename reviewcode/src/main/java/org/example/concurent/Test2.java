package org.example.concurent;

import java.util.concurrent.TimeUnit;

//增加一个
public class Test2 {
    public static void main(String[] args) throws InterruptedException {
        t2 t2 = new t2();
        //t2 t3=new t2();
        new Thread(()->{
            try {
                t2.sendsms();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"A").start();

        TimeUnit.SECONDS.sleep(1);

        new Thread(t2::hello,"B").start();
    }
}
class t2{
    //synchronized锁的对象是方法的调用者（单个new的对象）
    public synchronized void sendsms() throws InterruptedException {
        TimeUnit.SECONDS.sleep(6);
        System.out.println("发短信");
    }
    public synchronized void call() {
        System.out.println("打电话");
    }
    public void hello(){
        System.out.println("hello");
    }
}
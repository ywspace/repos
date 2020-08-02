package org.example.volatiles;

import java.util.concurrent.TimeUnit;

public class VolatileDemo {
    public static void main(String[] args) {

      //  seeOkByVolatile();

        MyData myData=new MyData();
        for (int i = 0; i <20 ; i++) {
             new Thread(()->{
                 for (int j = 0; j < 1000; j++) {
                     myData.plus();
                 }
             },String.valueOf(i)).start();
        }

        while (Thread.activeCount()>2){
            Thread.yield();
        }
        System.out.println("number:" +myData.number);

    }

    //volatile 保证可见性  及时通知其他线程主物理内存值已经被修改
    private static void seeOkByVolatile() {
        MyData myData=new MyData();

        new Thread(()->{

            System.out.println(Thread.currentThread().getName()+" come in");

            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            myData.addTo60();

            System.out.println(Thread.currentThread().getName()+"  update number value:"+myData.number);


        },"aaa").start();


        while (myData.number==0){
            //主线程 一直等待，直到number被重新赋值
        }

        System.out.println(Thread.currentThread().getName()+"  number value:"+myData.number);
    }


}
class MyData
{
    volatile Integer number=0;//包装类 不赋值 则会报空指针

    public void addTo60(){
        this.number=60;
    }
  // public synchronized void  plus(){
    public  void  plus(){
        this.number++;
    }
}

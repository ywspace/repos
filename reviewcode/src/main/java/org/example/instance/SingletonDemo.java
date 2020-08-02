package org.example.instance;

import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

public class SingletonDemo {

    //volatile 可以禁止指令重排，多线程下保证安全。
    private static volatile SingletonDemo instance;

    private SingletonDemo(){
        System.out.println(Thread.currentThread().getName()+" instance");
    }

    public static SingletonDemo getInstance(){

        if(instance==null){
            synchronized (SingletonDemo.class){
                if(instance==null)
                {
                    instance=new SingletonDemo();
                }
            }
        }
        return instance;

    }

}

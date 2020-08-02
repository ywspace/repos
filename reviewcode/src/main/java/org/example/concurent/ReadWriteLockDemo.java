package org.example.concurent;


import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 读写分离
 */
public class ReadWriteLockDemo {

    public static void main(String[] args) {
        MyCache cache=new MyCache();

        for (int i = 0; i <6; i++) {
            final int finalI = i;
            new Thread(()->{
                cache.put(String.valueOf(finalI),String.valueOf(finalI));

            },"W"+i).start();
        }

        for (int i = 0; i <6; i++) {
            final int finalI = i;
            new Thread(()->{
                cache.get(String.valueOf(finalI));

            },"R"+i).start();
        }

    }
}
class MyCache
{
    private volatile Map<String,Object> map=new HashMap<>();
    private ReentrantReadWriteLock rwlock=new ReentrantReadWriteLock();


    public void put(String key,Object value){

        rwlock.writeLock().lock();

        try {
            System.out.println(Thread.currentThread().getName()+" 正在写入...");
            map.put(key,value);
            System.out.println(Thread.currentThread().getName()+" 写入完成...");
        } finally {
            rwlock.writeLock().unlock();
        }
    }

    public void get(String key){
        rwlock.readLock().lock();

        try {
            System.out.println(Thread.currentThread().getName()+" 开始读取...");
            Object result= map.get(key);
            System.out.println(Thread.currentThread().getName()+" 读取完成："+result);
        } catch (Exception exception) {
            exception.printStackTrace();
        } finally {
            rwlock.readLock().unlock();
        }
    }


}

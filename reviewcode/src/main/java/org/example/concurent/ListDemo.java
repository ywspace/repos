package org.example.concurent;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

public class ListDemo {

    /**
     *   问题： 单个线程使用ArrayList 没问题，当多个线程时将报  java.util.ConcurrentModificationException  异常。
     *   解决：1、使用Vector  (jdk 1.0)
     *        2、使用集合工具类：Collections
     *        3、使用 CopyOnWriteArrayList
     * @param args
     */
    public static void main(String[] args) {

        ArrayList<String> list = new ArrayList<>();

      // List<String> lists= new Vector<>();

       // List<String> lists = Collections.synchronizedList(new ArrayList<>());

       // List<String> lists = new CopyOnWriteArrayList<>();

        for (int i=0;i<30;i++) {
            new Thread(()->{

                list.add(UUID.randomUUID().toString().substring(0,5));

                System.out.println(list);
            },"AAA"+i).start();
        }



    }
}

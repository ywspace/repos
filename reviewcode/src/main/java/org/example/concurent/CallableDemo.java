package org.example.concurent;

import java.util.concurrent.*;

public class CallableDemo {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        FutureTask<Integer> task=new FutureTask<>(new MyThread());
        task.run();
      
        Integer integer = task.get();
        System.out.println(integer);
    }

}
class MyThread implements Callable<Integer>
{

    @Override
    public Integer call() throws Exception {
        System.out.println("AAAAA");
        TimeUnit.SECONDS.sleep(5);
        return 123;
    }
}
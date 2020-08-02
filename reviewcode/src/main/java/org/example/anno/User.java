package org.example.anno;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

/**
 * @PostConstruct是java自己的注解，用来修饰非静态方法，
 * 被@PostConstruct修饰的方法会在服务器家在servlet的时候运行，
 * 并且只会被服务器执行一次，在bean初始化过程中执行顺序：
 * 【加载顺序】： 静态代码块 -Constructor（构造方法）-@Autowired（依赖注入）-@PostConstruct（注释方法） - InitializingBean (afterPropertiesSet)
 */

@SpringBootApplication
public class User implements InitializingBean {

    @Autowired
    private Pepl pepl;

    static {
        System.out.println(" static 静态代码块");
    }

    public User(){
        System.out.println(" 构造函数");
    }

    @PostConstruct
    public void init(){
        System.out.println("@PostConstruct");
    }
    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("afterPropertiesSet");
    }

    public static void main(String[] args) {

        SpringApplication.run(User.class,args);
    }


}


package org.example.anno;

import org.springframework.stereotype.Component;

@Component
public class Pepl {
    public Pepl(){
        System.out.println("pepl autowired");
    }
}

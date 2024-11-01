package com.chxt.fantasticmonkey;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.chxt.fantasticmonkey.infrastructure")
@MapperScan("com.chxt.fantasticmonkey.controller.download")
@MapperScan("com.chxt.fantasticmonkey.trigger.download")
public class FantasticmonkeyApplication {

    public static void main(String[] args) {
        SpringApplication.run(FantasticmonkeyApplication.class, args);
    }

}

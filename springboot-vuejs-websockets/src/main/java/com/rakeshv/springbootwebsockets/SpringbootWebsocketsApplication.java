package com.rakeshv.springbootwebsockets;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SpringbootWebsocketsApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootWebsocketsApplication.class, args);
    }

}

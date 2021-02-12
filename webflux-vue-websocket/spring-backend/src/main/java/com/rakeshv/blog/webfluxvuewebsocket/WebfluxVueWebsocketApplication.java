package com.rakeshv.blog.webfluxvuewebsocket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class WebfluxVueWebsocketApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebfluxVueWebsocketApplication.class, args);
	}

}

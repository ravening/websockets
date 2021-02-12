package com.rakeshv.springbootwebsockets.controller;

import com.rakeshv.springbootwebsockets.models.Greeting;
import com.rakeshv.springbootwebsockets.models.HelloMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

@Controller
public class GreetingController {

    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public Greeting greeting(HelloMessage message) throws Exception {
        return Greeting.builder().content("Hello " + HtmlUtils.htmlEscape(message.getName())).build();
    }
}

package com.rakeshv.blog.webfluxvuewebsocket;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class HelloMessage {

    public String name;

    @JsonCreator
    public HelloMessage(
            @JsonProperty("name") String name) {
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

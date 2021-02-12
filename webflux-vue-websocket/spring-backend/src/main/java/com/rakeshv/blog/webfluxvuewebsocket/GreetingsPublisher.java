package com.rakeshv.blog.webfluxvuewebsocket;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.vavr.control.Try;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import reactor.core.publisher.FluxSink;

import java.util.Date;
import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.function.Consumer;

@Component
public class GreetingsPublisher implements Consumer<FluxSink<String>> {
    private static final Logger log = LoggerFactory.getLogger(GreetingsPublisher.class);

    private ObjectMapper jsonMapper = new ObjectMapper();
    private final BlockingQueue<String> queue = new LinkedBlockingQueue<>();
    private final Executor executor = Executors.newSingleThreadExecutor();

    public boolean push(String greeting) {
        return queue.offer(greeting);
    }

    @Override
    public void accept(FluxSink<String> sink) {
        this.executor.execute(() -> {
            while (true) {
                Try.of(() -> {
                    final String greeting = queue.take();
                    return sink.next(greeting);
                })
                        .onFailure(ex -> log.error("Could not take greeting from queue", ex));
            }
        });
    }

    @Scheduled(fixedRate = 5000)
    public void pushMessages() throws JsonProcessingException {
        Greeting message = new Greeting("Hello world at " + new Date());
        push(jsonMapper.writeValueAsString(message));
    }
}

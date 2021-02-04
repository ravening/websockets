package com.corundumstudio.socketio.demo;

import com.corundumstudio.socketio.listener.*;
import com.corundumstudio.socketio.*;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ChatLauncher {

    public static void main(String[] args) throws InterruptedException {

        Configuration config = new Configuration();
        config.setHostname("localhost");
        config.setPort(9092);

        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);
        final SocketIOServer server = new SocketIOServer(config);
        server.addEventListener("chatevent", ChatObject.class, new DataListener<ChatObject>() {
            @Override
            public void onData(SocketIOClient client, ChatObject data, AckRequest ackRequest) {
                // broadcast messages to all clients
                server.getBroadcastOperations().sendEvent("chatevent", data);
                client.sendEvent(data.getUserName(), "sending only to you " + data.getUserName());
            }
        });


        server.start();

//        executorService.scheduleAtFixedRate(() -> sendMessage(server), 1, 1, TimeUnit.SECONDS);
        Thread.sleep(Integer.MAX_VALUE);

        server.stop();
    }

    public static void sendMessage(SocketIOServer server) {
        ChatObject data = new ChatObject("test", "testing the socketio");
        server.getBroadcastOperations().sendEvent("chatevent", data);
        server.getBroadcastOperations().sendEvent("test", "wassup");

    }

}

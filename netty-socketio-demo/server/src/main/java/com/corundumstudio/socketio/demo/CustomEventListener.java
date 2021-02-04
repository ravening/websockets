package com.corundumstudio.socketio.demo;

import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.annotation.OnConnect;
import com.corundumstudio.socketio.annotation.OnDisconnect;
import com.corundumstudio.socketio.annotation.OnEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CustomEventListener {
    private static final Logger LOGGER = LoggerFactory.getLogger(CustomEventListener.class);

    @OnConnect
    public void onConnect(SocketIOClient client) {
        LOGGER.info("Client connected with id " + client.getSessionId().toString());
    }

    @OnEvent("chatevent")
    public void onEvent(SocketIOClient client, ChatObject object) {
        LOGGER.info("Received from client id " + client.getSessionId().toString() + " message: " + object.getMessage());
        LOGGER.error("sending the message back");
        sendEvent(client, object);
    }

    @OnDisconnect
    public void onDisconnect(SocketIOClient client) {
        LOGGER.info("Client disconnected "+ client.getSessionId().toString());
    }

    private void sendEvent(SocketIOClient client, ChatObject object) {
        object.setMessage(object.getMessage() + " wassup from backend");
        client.sendEvent("chatevent", object);
    }

}

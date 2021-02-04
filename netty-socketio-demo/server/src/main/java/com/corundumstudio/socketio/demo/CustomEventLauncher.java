package com.corundumstudio.socketio.demo;

import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketConfig;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;

public class CustomEventLauncher {
    public static void main(String[] args) {
        Configuration configuration = new Configuration();
        configuration.setPort(9092);

        SocketConfig socketConfig = new SocketConfig();
        socketConfig.setReuseAddress(true);
        configuration.setSocketConfig(socketConfig);

        final SocketIOServer server = new SocketIOServer(configuration);
        CustomEventListener customEventListener = new CustomEventListener();
        server.addListeners(customEventListener);
        server.start();
    }
}

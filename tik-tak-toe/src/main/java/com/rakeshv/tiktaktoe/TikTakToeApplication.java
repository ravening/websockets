package com.rakeshv.tiktaktoe;

import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketConfig;
import com.corundumstudio.socketio.SocketIONamespace;
import com.corundumstudio.socketio.SocketIOServer;
import com.rakeshv.tiktaktoe.engine.GameEngine;
import com.rakeshv.tiktaktoe.engine.GameEngineImpl;
import com.rakeshv.tiktaktoe.engine.GameValidator;
import com.rakeshv.tiktaktoe.listeners.GameEventListener;
import com.rakeshv.tiktaktoe.listeners.PlayerMoveEventListener;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TikTakToeApplication {

    public static void main(String[] args) {

        Configuration configuration = new Configuration();
        configuration.setPort(9092);
        SocketConfig socketConfig = new SocketConfig();
        socketConfig.setReuseAddress(true);
        configuration.setSocketConfig(socketConfig);

        final SocketIOServer server = new SocketIOServer(configuration);
        GameEngine gameEngine = new GameEngineImpl();
        server.addListeners(new PlayerMoveEventListener(gameEngine));
        server.addListeners(new GameEventListener(gameEngine));
        final SocketIONamespace namespace = server.addNamespace("/client");
        namespace.addListeners(new PlayerMoveEventListener(gameEngine));
        namespace.addListeners(new GameEventListener(gameEngine));
        server.start();
//        SpringApplication.run(TikTakToeApplication.class, args);
    }

}

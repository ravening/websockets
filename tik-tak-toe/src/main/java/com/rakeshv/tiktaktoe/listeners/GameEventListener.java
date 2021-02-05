package com.rakeshv.tiktaktoe.listeners;

import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.annotation.OnConnect;
import com.corundumstudio.socketio.annotation.OnDisconnect;
import com.corundumstudio.socketio.annotation.OnEvent;
import com.rakeshv.tiktaktoe.engine.GameEngine;
import com.rakeshv.tiktaktoe.engine.GameValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GameEventListener {
    private static final Logger LOGGER = LoggerFactory.getLogger(GameEventListener.class);
    private final GameEngine gameEngine;

    public GameEventListener(GameEngine gameEngine) {
        this.gameEngine = gameEngine;
    }
    @OnConnect
    public void onConnect(SocketIOClient client) throws Exception {
        LOGGER.info("=====================Client connected: session id is " + client.getSessionId());
        this.gameEngine.createGame(client);
    }

    @OnDisconnect
    public void onDisconnect(SocketIOClient client) {
        LOGGER.info("client disconnected with session id " + client.getSessionId().toString());
        this.gameEngine.restartGame(client);
    }

    @OnEvent("reset-game")
    private void resetGame(SocketIOClient client) {
        System.err.println("Restting game");
        this.gameEngine.restartGame(client);
    }
}

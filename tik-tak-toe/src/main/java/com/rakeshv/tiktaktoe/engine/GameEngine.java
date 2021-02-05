package com.rakeshv.tiktaktoe.engine;

import com.corundumstudio.socketio.SocketIOClient;

import java.util.Optional;

public interface GameEngine {
    void createGame(SocketIOClient client) throws Exception;

    void joinGame(SocketIOClient client) throws Exception;

    void leaveGame(SocketIOClient client) throws Exception;

    void restartGame(SocketIOClient client);

    Optional<GameValidator> getGame(SocketIOClient client);
}

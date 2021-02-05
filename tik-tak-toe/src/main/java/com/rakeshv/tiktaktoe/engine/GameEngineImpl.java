package com.rakeshv.tiktaktoe.engine;

import com.corundumstudio.socketio.SocketIOClient;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class GameEngineImpl implements GameEngine {

    Map<String, GameValidator> games = new ConcurrentHashMap<>();

    @Override
    public void createGame(SocketIOClient client) throws Exception {
        if (this.games.containsKey(client.getSessionId().toString())) {
            restartGame(client);
        } else {
            this.games.putIfAbsent(client.getSessionId().toString(), new GameValidator());
        }
    }

    @Override
    public void joinGame(SocketIOClient client) throws Exception {

    }

    @Override
    public void leaveGame(SocketIOClient client) throws Exception {

    }

    @Override
    public void restartGame(SocketIOClient client) {
        GameValidator validator = this.games.get(client.getSessionId().toString());
        validator.resetGame();
    }

    @Override
    public Optional<GameValidator> getGame(SocketIOClient client) {
        return Optional.ofNullable(this.games.get(client.getSessionId().toString()));
    }
}

package com.rakeshv.tiktaktoe.listeners;

import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.annotation.OnEvent;
import com.rakeshv.tiktaktoe.TextMessage;
import com.rakeshv.tiktaktoe.engine.GameEngine;
import com.rakeshv.tiktaktoe.engine.GameValidator;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

@Data
@NoArgsConstructor
@Builder
public class PlayerMoveEventListener {
    private GameEngine gameEngine;
    private static final Logger LOGGER = LoggerFactory.getLogger(PlayerMoveEventListener.class);

    public PlayerMoveEventListener(GameEngine gameEngine) {
        this.gameEngine = gameEngine;
    }

    @OnEvent("move")
    public void move(SocketIOClient client, TextMessage message) {
        Optional<GameValidator> validatorOptional = this.gameEngine.getGame(client);
        if (validatorOptional.isPresent()) {
            GameValidator game = validatorOptional.get();
            game.makeMove(message.getX(), message.getY(), message.isFirstPlayer());
            if (game.isGameOver()){
                client.sendEvent("gameover",  game.getWinner() + " wins the game");
                game.resetGame();
            }
        }
    }
}

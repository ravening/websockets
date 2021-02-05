package com.rakeshv.tiktaktoe.engine;

import com.corundumstudio.socketio.SocketIOClient;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class GameValidator {
    int[][] board;
    int[] row;
    int[] column;
    int diagonal = 0;
    int antidiagonal = 0;
    Map<String, GameValidator> games;
    String winner = "";

    public GameValidator() {
        board = new int[3][3];
        row = new int[3];
        column = new int[3];
        games = new ConcurrentHashMap<>();
    }

    public void makeMove(int x, int y, boolean player1) {
        int value = player1 ? 1 : -1;
        board[x][y] = value;
        row[x] += value;
        column[y] += value;
        if (x == y) {
            diagonal += value;
        }
        if (x + y == row.length) {
            antidiagonal += value;
        }

        if (row[x] == row.length || column[y] == row.length || diagonal == row.length || antidiagonal == row.length){
            winner = "Player1";
        }
        if (row[x] == -row.length || column[y] == -row.length || diagonal == -row.length || antidiagonal == -row.length){
            winner = "Player2";
        }
    }

    public boolean isGameOver() {
        return !winner.equalsIgnoreCase("");
    }

    public String getWinner() {
        return winner;
    }

    public void createGame(SocketIOClient client) {
        if (this.games.containsKey(client.getSessionId().toString())) {
            resetGame();
        } else {
            System.err.println("Creating new game");
            this.games.putIfAbsent(client.getSessionId().toString(), new GameValidator());
        }
    }

    public GameValidator getGame(SocketIOClient client) {
        return this.games.get(client.getSessionId().toString());
    }

    public void resetGame() {
        Arrays.fill(row, 0);
        Arrays.fill(column, 0);
        Arrays.stream(board).forEach(arr -> Arrays.fill(arr, 0));
    }
}

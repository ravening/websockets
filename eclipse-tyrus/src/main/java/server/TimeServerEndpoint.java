package server;

import jakarta.websocket.CloseReason;
import jakarta.websocket.OnClose;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.ServerEndpoint;

import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;
import java.util.stream.IntStream;

@ServerEndpoint(value = "/time")
public class TimeServerEndpoint {
    private final Logger logger = Logger.getLogger(this.getClass().getName());
    private static Set<Session> allSessions;
    static ScheduledExecutorService timer = Executors.newSingleThreadScheduledExecutor();
    DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

    @OnOpen
    public void onOpen(Session session) {
        logger.info("Connected, sessionID = " + session.getId());
        allSessions = session.getOpenSessions();
        timer.scheduleAtFixedRate(() -> sendTimeToClient(allSessions), 0, 1, TimeUnit.SECONDS);
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        logger.info("Received: " + message);
    }

    @OnClose
    public void onClose(Session session, CloseReason closeReason) {
        System.out.println("Session " + session.getId() +
                " closed because " + closeReason);
    }

    private void sendTimeToClient(Set<Session> allSessions) {
        allSessions.forEach(s -> {
            try {
                s.getBasicRemote().sendText("Local time : " + LocalTime.now().format(timeFormatter));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}

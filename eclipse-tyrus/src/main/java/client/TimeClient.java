package client;

import jakarta.websocket.ClientEndpoint;
import jakarta.websocket.CloseReason;
import jakarta.websocket.DeploymentException;
import jakarta.websocket.OnClose;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import org.glassfish.tyrus.client.ClientManager;
import server.TimeServerEndpoint;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.CountDownLatch;
import java.util.logging.Logger;

@ClientEndpoint
public class TimeClient {
    private static CountDownLatch latch;
    private final Logger logger = Logger.getLogger(this.getClass().getName());

    @OnOpen
    public void onOpen(Session session) {
        System.out.println ("--- Connected " + session.getId());
        try {
            session.getBasicRemote().sendText("start");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        logger.info("Received " + message);
    }

    @OnClose
    public void onClose(Session session, CloseReason closeReason) {
        System.out.println("Session " + session.getId() +
                " closed because " + closeReason);
    }

    public static void main(String[] args) {
        latch = new CountDownLatch(1);
        ClientManager client = ClientManager.createClient();
        try {
            URI uri = new URI("ws://localhost:8026/websockets/time");
            client.connectToServer(TimeClient.class, uri);
            latch.await();
        } catch (DeploymentException | URISyntaxException | InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

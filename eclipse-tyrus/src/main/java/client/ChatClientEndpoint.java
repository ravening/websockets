package client;

import jakarta.websocket.ClientEndpoint;
import jakarta.websocket.CloseReason;
import jakarta.websocket.DeploymentException;
import jakarta.websocket.OnClose;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import org.glassfish.tyrus.client.ClientManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.CountDownLatch;
import java.util.logging.Logger;

@ClientEndpoint
public class ChatClientEndpoint {
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
    public String onMessage(String message, Session session) {
        BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
        try {
            logger.info("Received " + message);
            return bufferRead.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @OnClose
    public void onClose(Session session, CloseReason closeReason) {
        System.out.println("Session " + session.getId() +
                " closed because " + closeReason);
        latch.countDown();
    }

    public static void main(String[] args) {
        latch = new CountDownLatch(1);
        ClientManager client = ClientManager.createClient();
        try {
            URI uri = new URI("ws://localhost:8025/websockets/server");
            client.connectToServer(ChatClientEndpoint.class, uri);
            latch.await();
        } catch (DeploymentException | URISyntaxException | InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

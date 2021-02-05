package com.pluralsight;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@ServerEndpoint("/websocket/chat")
public class ChatEndpoint {

    static List<ChatEndpoint> clients = new CopyOnWriteArrayList<ChatEndpoint>();
    Session session;

    @OnOpen
    public void onOpen(Session session, EndpointConfig __) {
        this.session = session;
        clients.add(this);
    }

    @OnClose
    public void onClose(Session session, CloseReason reason) {
        System.out.println("socket closed: " + reason.getReasonPhrase());
        clients.remove(this);
    }


    ByteArrayOutputStream buffer = new ByteArrayOutputStream();


    @OnMessage
    public void onMessage(ByteBuffer byteBuffer, boolean complete) {
        try {
            buffer.write(byteBuffer.array());
            if (complete) {

                FileOutputStream fos = null;
                try {
                    fos = new FileOutputStream("c:\\temp\\image.jpg");
                    fos.write(buffer.toByteArray());
                } finally {
                    if (fos != null) {
                        fos.flush();
                        fos.close();
                    }
                }
                for (ChatEndpoint client : clients) {
                    final ByteBuffer sendData = ByteBuffer.allocate(buffer.toByteArray().length);
                    sendData.put(buffer.toByteArray());
                    sendData.rewind();
                    client.session.getAsyncRemote().sendBinary(sendData, new SendHandler() {
                        @Override
                        public void onResult(SendResult sendResult) {
                            System.out.println(sendResult.isOK());
                        }
                    });
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @OnMessage
    public String onMessage(String message) {
        //broadcast((String) message);
        return "{\"message\":\"ok\"}";
    }

    private void broadcast(String message) {
        for (ChatEndpoint client : clients) {
            try {
                client.session.getBasicRemote().sendText(message);
            } catch (IOException e) {
                clients.remove(this);
                try {
                    client.session.close();
                } catch (IOException e1) {
                    // do nothing
                }
            }
        }
    }
}

package com.robin.websocket;

import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

@Component
@ServerEndpoint("/webSocket/{oid}")
public class WebSocketServer {
    private static ConcurrentHashMap<String, Session> sessionMap = new ConcurrentHashMap<>();
    @OnOpen
    public void open(@PathParam("oid")String orderId,Session session){
        sessionMap.put(orderId, session);
    }
    @OnClose
    public void close(@PathParam("oid")String orderId){
        sessionMap.remove(orderId);
    }

    public static void sendMsg(String orderId,String msg){
        Session session = sessionMap.get(orderId);
        try {
            session.getBasicRemote().sendText(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

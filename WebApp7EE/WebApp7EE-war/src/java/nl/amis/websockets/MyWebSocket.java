package nl.amis.websockets;

import java.io.IOException;
import java.io.Serializable;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import nl.amis.events.CDIJmsEvent;
import nl.amis.jms.JmsSessionBean;

/**
 *
 * @author edwin
 */
@ServerEndpoint("/mywebsocket")
public class MyWebSocket implements Serializable {

    @Inject
    JmsSessionBean jmsBean;
    
    
    private static final Set<Session> sessions = Collections.synchronizedSet(new HashSet<Session>());

    @OnOpen
    public void onOpen(final Session session) {
        try {
            session.getBasicRemote().sendText("session opened");
            sessions.add(session);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @OnMessage
    public void onMessage(final String message, final Session client) {
        try {
            client.getBasicRemote().sendText("sending message to jmsBean...");
        } catch (IOException ex) {
            
            ex.printStackTrace();
        }

        if (jmsBean != null) {
            jmsBean.sendMessage(message);
        } else {
            System.out.println("jmsBean is null");
        }
    }

    @OnClose
    public void onClose(final Session session) {
        try {
            session.getBasicRemote().sendText("WebSocket Session closed");
            sessions.remove(session);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void onJMSMessage(@Observes @CDIJmsEvent Message msg) {
        System.out.println("Got JMS Message at WebSocket!");
        try {
            for (Session s : sessions) {
                s.getBasicRemote().sendText("message from JMS: " + msg.getBody(String.class));
            }
        } catch (IOException | JMSException ex) {
            ex.printStackTrace();
        }
    }

    
}

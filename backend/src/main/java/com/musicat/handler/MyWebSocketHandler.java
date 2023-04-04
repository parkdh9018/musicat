package com.musicat.handler;


import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

@Component
public class MyWebSocketHandler implements StompMessageHandler {

  private List<WebSocketSession> sessions = new CopyOnWriteArrayList<>();

  @Override
  public void afterConnected(WebSocketSession session, StompHeaderAccessor accessor) {
    sessions.add(session);
  }

  @Override
  public void handleFrame(WebSocketSession session, StompHeaderAccessor accessor, Object payload) {
    // Handle incoming messages
  }

  @Override
  public void handleException(WebSocketSession session, StompCommand command, StompHeaderAccessor accessor, byte[] payload, Throwable exception) {
    // Handle exceptions
  }

  @Override
  public void handleTransportError(WebSocketSession session, Throwable exception) {
    // Handle transport errors
  }

  public int getSessionCount() {
    return sessions.size();
  }
}

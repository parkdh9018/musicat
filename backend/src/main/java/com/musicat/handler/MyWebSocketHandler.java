package com.musicat.handler;


import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

@Component
public class MyWebSocketHandler implements WebSocketHandler {

  private List<WebSocketSession> sessions = new CopyOnWriteArrayList<>();

  @Override
  public void afterConnectionEstablished(WebSocketSession session) throws Exception {
    sessions.add(session);
  }

  @Override
  public void handleMessage(WebSocketSession session, WebSocketMessage<?> message)
      throws Exception {

  }

  @Override
  public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
    sessions.remove(session);
  }

  @Override
  public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
    // Handle transport errors
  }

  @Override
  public boolean supportsPartialMessages() {
    return false;
  }

  public int getSessionCount() {
    return sessions.size();
  }
}

package com.musicat.handler;

import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.web.socket.WebSocketSession;

public interface StompMessageHandler {
  void afterConnected(WebSocketSession session, StompHeaderAccessor accessor);
  void handleFrame(WebSocketSession session, StompHeaderAccessor accessor, Object payload);
  void handleException(WebSocketSession session, StompCommand command, StompHeaderAccessor accessor, byte[] payload, Throwable exception);
  void handleTransportError(WebSocketSession session, Throwable exception);
}

//package com.musicat.handler;
//
//import java.util.concurrent.ConcurrentHashMap;
//import org.springframework.web.socket.CloseStatus;
//import org.springframework.web.socket.WebSocketSession;
//import org.springframework.web.socket.handler.TextWebSocketHandler;
//
//public class MyWebSocketHandler extends TextWebSocketHandler {
//  private ConcurrentHashMap<String, WebSocketSession> sessions = new ConcurrentHashMap<>();
//
//  @Override
//  public void afterConnectionEstablished(WebSocketSession session) {
//    sessions.put(session.getId(), session);
//    System.out.println("현재 접속자 수: " + sessions.size());
//  }
//
//  @Override
//  public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
//    sessions.remove(session.getId());
//    System.out.println("현재 접속자 수: " + sessions.size());
//  }
//
//  public int getConnectedUsersCount() {
//    return sessions.size();
//  }
//
//}

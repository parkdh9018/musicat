//package com.musicat.interceptor;
//
//import com.musicat.service.socket.WebSocketUserCounterService;
//import lombok.RequiredArgsConstructor;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.messaging.Message;
//import org.springframework.messaging.MessageChannel;
//import org.springframework.messaging.simp.stomp.StompCommand;
//import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
//import org.springframework.messaging.support.ChannelInterceptorAdapter;
//import org.springframework.stereotype.Component;
//
//@Component
//@RequiredArgsConstructor
//public class WebSocketUserCounterInterceptor extends ChannelInterceptorAdapter {
//
//  private final WebSocketUserCounterService userCounter;
//  // logger 정의
//  private static final Logger logger = LoggerFactory.getLogger(WebSocketUserCounterInterceptor.class);
//
//  @Override
//  public Message<?> preSend(Message<?> message, MessageChannel channel) {
//    StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
//    StompCommand command = accessor.getCommand();
//
//    if (command != null) {
//      if (command.equals(StompCommand.CONNECT)) {
//        userCounter.increment();
//        logger.debug("세션 연결 정보 : {}", message.getPayload());
//        logger.debug("세션 연결됨 : {}", userCounter.getCount());
//      } else if (command.equals(StompCommand.DISCONNECT)) {
//        if (userCounter.getCount() >= 1) {
//          userCounter.decrement();
//          logger.debug("세션 끊어짐 : {}", userCounter.getCount());
//        }
//      }
//    }
//
//    return super.preSend(message, channel);
//  }
//}

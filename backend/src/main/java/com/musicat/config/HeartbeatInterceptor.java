package com.musicat.config;

import java.util.concurrent.atomic.AtomicInteger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;

public class HeartbeatInterceptor implements ChannelInterceptor {

  private static final Logger logger = LoggerFactory.getLogger(HeartbeatInterceptor.class);

  private final AtomicInteger connectedClients = new AtomicInteger(0);

  @Override
  public Message<?> preSend(Message<?> message, MessageChannel channel) {
    StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
    if (StompCommand.CONNECT.equals(accessor.getCommand())) {
      logger.debug("CONNECT message received");
      connectedClients.incrementAndGet();
    } else if (StompCommand.DISCONNECT.equals(accessor.getCommand())) {
      logger.debug("DISCONNECT message received");
      connectedClients.decrementAndGet();
    } else if (StompCommand.SUBSCRIBE.equals(accessor.getCommand())) {
      logger.debug("SUBSCRIBE message received");
    } else if (StompCommand.SEND.equals(accessor.getCommand())) {
      logger.debug("SEND message received");
    } else if (StompCommand.STOMP.equals(accessor.getCommand())) {
      logger.debug("STOMP message received");
    } else {
      logger.debug("Unknown command received");
    }
    logger.debug("Connected clients: {}", connectedClients.get());
    return message;
  }
}

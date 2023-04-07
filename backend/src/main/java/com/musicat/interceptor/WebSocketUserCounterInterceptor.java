package com.musicat.interceptor;

import com.musicat.service.socket.WebSocketUserCounterService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptorAdapter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WebSocketUserCounterInterceptor extends ChannelInterceptorAdapter {

  private final WebSocketUserCounterService userCounter;

  @Override
  public Message<?> preSend(Message<?> message, MessageChannel channel) {
    StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
    StompCommand command = accessor.getCommand();

    if (command != null) {
      if (command.equals(StompCommand.CONNECT)) {
        userCounter.increment();
      } else if (command.equals(StompCommand.DISCONNECT)) {
        if (userCounter.getCount() >= 1) {
          userCounter.decrement();
        }
      }
    }

    System.out.println("@@@@@@@@    " + userCounter.getCount());
    return super.preSend(message, channel);
  }
}

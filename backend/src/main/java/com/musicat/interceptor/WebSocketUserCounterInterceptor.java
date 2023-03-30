//package com.musicat.interceptor;
//
//import com.musicat.service.WebSocketUserCounter;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.messaging.Message;
//import org.springframework.messaging.MessageChannel;
//import org.springframework.messaging.support.ChannelInterceptorAdapter;
//import org.springframework.stereotype.Component;
//import org.springframework.web.socket.messaging.SessionConnectEvent;
//import org.springframework.web.socket.messaging.SessionDisconnectEvent;
//
//@Component
//public class WebSocketUserCounterInterceptor extends ChannelInterceptorAdapter {
//
//    @Autowired
//    private WebSocketUserCounter userCounter;
//
//    @Override
//    public Message<?> preSend(Message<?> message, MessageChannel channel) {
//        if (message instanceof SessionConnectEvent) {
//            userCounter.increment();
//        } else if (message instanceof SessionDisconnectEvent) {
//            userCounter.decrement();
//        }
//        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@    " + userCounter.getCount());
//        return super.preSend(message, channel);
//    }
//}

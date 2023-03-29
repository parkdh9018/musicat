//package com.musicat.controller;
//
//import com.musicat.service.StoryService;
//import lombok.RequiredArgsConstructor;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.messaging.handler.annotation.DestinationVariable;
//import org.springframework.messaging.handler.annotation.Header;
//import org.springframework.messaging.handler.annotation.MessageMapping;
//import org.springframework.messaging.simp.SimpMessagingTemplate;
//import org.springframework.stereotype.Controller;
//
//@Controller
//@RequiredArgsConstructor
//public class ChatControllerV2 {
//
//    // logger 정의
//    private static final Logger logger = LoggerFactory.getLogger(ChatControllerV2.class);
//
//    private final SimpMessagingTemplate messagingTemplate;
//
//    @MessageMapping("/chat/{userId}")
//    public void sendToUser(@DestinationVariable String userId, String message, @Header("simpSessionId") String sessionId) {
//        logger.debug("{} 세션으로 요청옴 !!!!!", sessionId);
//        System.out.println("userId : " + userId + ", message : " + message);
//        messagingTemplate.convertAndSendToUser(userId, "/topic/messages", message);
//    }
//}

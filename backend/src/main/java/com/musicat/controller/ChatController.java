package com.musicat.controller;


import com.musicat.data.dto.MessageDto;
import com.musicat.service.PerspectiveService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class ChatController {
    // logger 정의
    private static final Logger logger = LoggerFactory.getLogger(StoryController.class);

    private final SimpMessagingTemplate template;

    // Service 정의
    private final PerspectiveService perspectiveService;

    @MessageMapping("/chat")
    public void send(MessageDto message) { // 프론트로부터 데이터가 넘어옴 -> 브로드캐스트로 전송

        boolean filterResult = perspectiveService.filterText(message.getContent());

        logger.debug("채팅 전송!!!! : {}, 받는사람 : {}, 비속어 필터 결과 : {}", message, message.getReceiver(), filterResult);

        if (!filterResult) message.setContent("[클린 채팅]을 사용해 주세요 :)");

        template.convertAndSend("/topic/messages", message); // 전체 전송


        //template.convertAndSendToUser(message.getReceiver(), "/topic/messages", message); // 특정 유저에게 전송
        //return message;
    }

}

package com.musicat.controller;

import com.musicat.data.dto.MessageDto;
import com.musicat.data.dto.SocketBaseDto;
import com.musicat.data.entity.item.Badge;
import com.musicat.service.PerspectiveService;
import com.musicat.service.user.UserService;
import com.musicat.util.SessionUtil;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;


@Controller
@RequiredArgsConstructor
public class ChatController {

    private static final String TYPE = "CHAT";
    private static final String OPERATION = "CHAT";

    // logger 정의
    private static final Logger logger = LoggerFactory.getLogger(StoryController.class);

    private final SimpMessagingTemplate template;

    // Service 정의
    private final PerspectiveService perspectiveService;
    private final UserService userService;

    @MessageMapping("/chat")
    public void send(MessageDto messageDto, @Header("simpSessionId") String sessionId) { // 프론트로부터 데이터가 넘어옴 -> 브로드캐스트로 전송

        long userSeq = messageDto.getSenderSeq();
        boolean filterResult = perspectiveService.filterText(messageDto.getContent());

        logger.debug("[채팅 도착] 보낸 사람 : {}, userSeq : {}, 뱃지 : {}, 메시지: {}, 비속어 필터 결과 : {}, 세션 Id : {}", messageDto.getSender(), userSeq, messageDto.getBadgeSeq(), messageDto,  filterResult, sessionId);

        // Ban 검사
        if (userService.isBan(messageDto.getSenderSeq())) {
            logger.debug("해당 유저는 Ban 상태임 !!");
            messageDto.setBan(true);
            messageDto.setContent("채팅 금지 상태입니다.");

            // 특정 유저에게만 데이터 전송
            template.convertAndSendToUser(sessionId, "/queue/chat/ban", messageDto, SessionUtil.createHeaders(sessionId));
            return;
        }

        if (!filterResult){ // 더티 채팅
            messageDto.setContent(messageDto.getSender() + " 님 [클린 채팅]을 사용해 주세요 :)");
            // Todo : userSeq 로 사용자 검색 후 -> DB에 경고 횟수 증가 시키기. 경고 수가 3초과하면 Ban 해버리기
            messageDto.setBadgeSeq(0); // 욕설 사용시 보여줄 배지 없음
        } else { // 클린 채팅
            // setBadge를 userBadge 정보로 설정하는 로직
            Badge badge = userService.getBage(userSeq);
            messageDto.setBadgeSeq(badge.getBadgeSeq());
        }

        SocketBaseDto<MessageDto> result = SocketBaseDto.<MessageDto>builder().type(TYPE).operation(OPERATION).data(messageDto).build();
        template.convertAndSend("/topic/chat", messageDto); // 전체 전송
    }
}

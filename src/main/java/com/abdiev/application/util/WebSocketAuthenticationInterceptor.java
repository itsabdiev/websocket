package com.abdiev.application.util;

import com.abdiev.application.repository.MessageRepository;
import com.abdiev.application.service.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
@Slf4j
public class WebSocketAuthenticationInterceptor implements ChannelInterceptor {
    private final JwtService jwtService;
    private final SimpMessagingTemplate messagingTemplate;
    private MessageRepository messageRepository;

    //TODO -> test SUBSCRIBE

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        log.info("");
        final var accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
        final var cmd = accessor.getCommand();
        String jwt = null;
        if (StompCommand.CONNECT == cmd || StompCommand.SEND == cmd) {
            final var requestTokenHeader = accessor.getFirstNativeHeader("Authorization");
            if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer")) {
                jwt = requestTokenHeader.substring(7);
            }
            if (jwtService.validateToken(jwt) == null){
                return null;
            }
        }
        return message;
    }

}

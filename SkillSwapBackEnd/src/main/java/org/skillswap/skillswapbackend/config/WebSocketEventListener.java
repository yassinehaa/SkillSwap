package org.skillswap.skillswapbackend.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.skillswap.skillswapbackend.Models.Message;
import org.skillswap.skillswapbackend.Models.MessageType;
import org.skillswap.skillswapbackend.Repositories.UserRepository;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component
@RequiredArgsConstructor
@Slf4j
public class WebSocketEventListener {
    private final SimpMessageSendingOperations messageTemplate;
    private final UserRepository userRepository;

    @EventListener
    public void handleWebsocketDisconnectListener(SessionDisconnectEvent event){
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        String username =(String) headerAccessor.getSessionAttributes().get("username");
        if (username != null) {
            log.info("User Disconnected : " + username);
            userRepository.findByEmail(username).ifPresent(user -> {
                var message = Message.builder()
                        .type(MessageType.LEAVE)
                        .sender(user)
                        .build();
                messageTemplate.convertAndSend("/topic/messages", message);
            });
        }
    }
}

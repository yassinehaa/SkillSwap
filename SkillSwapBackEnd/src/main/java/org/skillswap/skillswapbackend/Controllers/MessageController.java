package org.skillswap.skillswapbackend.Controllers;

import org.skillswap.skillswapbackend.Services.MessageService;
import org.skillswap.skillswapbackend.dto.MessageDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class MessageController {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    private MessageService messageService;

    @MessageMapping("/chat")
    public void sendMessage(@Payload MessageDTO messageDTO) {
        MessageDTO savedMessage = messageService.saveMessage(messageDTO);
        simpMessagingTemplate.convertAndSend("/user/" + savedMessage.getReceiverId() + "/queue/messages", savedMessage);
    }

    @GetMapping("/messages/{senderId}/{receiverId}")
    public ResponseEntity<List<MessageDTO>> getConversation(@PathVariable Long senderId, @PathVariable Long receiverId) {
        return ResponseEntity.ok(messageService.getConversation(senderId, receiverId));
    }
}
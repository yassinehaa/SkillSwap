package org.skillswap.skillswapbackend.Controllers;

import org.skillswap.skillswapbackend.Models.User;
import org.skillswap.skillswapbackend.Repositories.UserRepository;
import org.skillswap.skillswapbackend.Services.MessageService;
import org.skillswap.skillswapbackend.dto.ConversationDTO;
import org.skillswap.skillswapbackend.dto.MessageDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RestController
public class MessageController {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    private MessageService messageService;

    @Autowired
    private UserRepository userRepository;

    @MessageMapping("/chat")
    public void sendMessage(@Payload MessageDTO messageDTO) {
        MessageDTO savedMessage = messageService.saveMessage(messageDTO);
        simpMessagingTemplate.convertAndSend("/user/" + savedMessage.getReceiverId() + "/queue/messages", savedMessage);
    }

    @PostMapping("/messages")
    public ResponseEntity<MessageDTO> sendHttpMessage(@RequestBody MessageDTO messageDTO) {
        MessageDTO savedMessage = messageService.saveMessage(messageDTO);
        simpMessagingTemplate.convertAndSend("/user/" + savedMessage.getReceiverId() + "/queue/messages", savedMessage);
        return ResponseEntity.ok(savedMessage);
    }

    @GetMapping("/messages/{senderId}/{receiverId}")
    public ResponseEntity<List<MessageDTO>> getConversation(@PathVariable Long senderId, @PathVariable Long receiverId) {
        return ResponseEntity.ok(messageService.getConversation(senderId, receiverId));
    }

    @GetMapping("/messages/conversations")
    public ResponseEntity<List<ConversationDTO>> getConversations(Principal principal) {
        if (principal == null) {
            return ResponseEntity.status(403).build();
        }
        String username = principal.getName();
        Optional<User> userOptional = userRepository.findByEmail(username);
        if (userOptional.isEmpty()) {
            return ResponseEntity.status(403).build();
        }
        User user = userOptional.get();
        return ResponseEntity.ok(messageService.getConversations(user.getId()));
    }
}
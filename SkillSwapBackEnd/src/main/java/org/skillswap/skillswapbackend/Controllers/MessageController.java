package org.skillswap.skillswapbackend.Controllers;

import org.skillswap.skillswapbackend.Services.MessageService;
import org.skillswap.skillswapbackend.dto.MessageDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/messages")
public class MessageController {
    @Autowired
    private MessageService messageService;

    @PostMapping("/{senderId}/{receiverId}")
    public ResponseEntity<MessageDTO> sendMessage(
            @PathVariable Long senderId,
            @PathVariable Long receiverId,
            @RequestBody MessageDTO messageDTO) {
        return ResponseEntity.ok(messageService.sendMessage(senderId, receiverId, messageDTO));
    }

    @GetMapping("/conversation/{userId1}/{userId2}")
    public ResponseEntity<List<MessageDTO>> getConversation(
            @PathVariable Long userId1,
            @PathVariable Long userId2) {
        return ResponseEntity.ok(messageService.getConversation(userId1, userId2));
    }
}
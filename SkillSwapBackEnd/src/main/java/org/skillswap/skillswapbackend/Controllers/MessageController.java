package org.skillswap.skillswapbackend.Controllers;

import org.skillswap.skillswapbackend.Models.Message;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

@Controller
public class MessageController {

    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/messages")
    public Message sendMessage(@Payload Message message) {
        return message;
    }
    @MessageMapping("/chat.addUser")
    @SendTo("/topic/messages")
    public Message addUser (@Payload Message message, SimpMessageHeaderAccessor headerAccessor){
     headerAccessor.getSessionAttributes().put("username", message.getSender());
     return message;
    }
}

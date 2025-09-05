package org.skillswap.skillswapbackend.Services;

import org.modelmapper.ModelMapper;
import org.skillswap.skillswapbackend.Models.Message;
import org.skillswap.skillswapbackend.Models.User;
import org.skillswap.skillswapbackend.Repositories.MessageRepository;
import org.skillswap.skillswapbackend.Repositories.UserRepository;
import org.skillswap.skillswapbackend.dto.MessageDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.skillswap.skillswapbackend.dto.ConversationDTO;
import org.skillswap.skillswapbackend.dto.UserDTO;

import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    public MessageDTO saveMessage(MessageDTO messageDTO) {
        User sender = userRepository.findById(messageDTO.getSenderId()).orElseThrow(() -> new RuntimeException("Sender not found"));
        User receiver = userRepository.findById(messageDTO.getReceiverId()).orElseThrow(() -> new RuntimeException("Receiver not found"));

        Message message = new Message();
        message.setSender(sender);
        message.setReceiver(receiver);
        message.setContent(messageDTO.getContent());
        message.setTimestamp(new Date());

        Message savedMessage = messageRepository.save(message);
        return modelMapper.map(savedMessage, MessageDTO.class);
    }

    public List<MessageDTO> getConversation(Long senderId, Long receiverId) {
        return messageRepository.findConversation(senderId, receiverId)
                .stream()
                .map(message -> modelMapper.map(message, MessageDTO.class))
                .collect(Collectors.toList());
    }

    public List<ConversationDTO> getConversations(Long userId) {
        List<Message> messages = messageRepository.findConversations(userId);
        System.out.println("Messages for user " + userId + ": " + messages);
        Map<Long, List<Message>> conversations = messages.stream()
                .collect(Collectors.groupingBy(message -> {
                    if (message.getSender().getId().equals(userId)) {
                        return message.getReceiver().getId();
                    } else {
                        return message.getSender().getId();
                    }
                }));
        System.out.println("Conversations map: " + conversations);

        List<ConversationDTO> conversationDTOs = conversations.entrySet().stream()
                .map(entry -> {
                    User withUser = userRepository.findById(entry.getKey()).orElse(null);
                    Message lastMessage = entry.getValue().stream()
                            .max(Comparator.comparing(Message::getTimestamp))
                            .orElse(null);

                    ConversationDTO conversationDTO = new ConversationDTO();
                    conversationDTO.setWithUser(modelMapper.map(withUser, UserDTO.class));
                    conversationDTO.setLastMessage(modelMapper.map(lastMessage, MessageDTO.class));
                    return conversationDTO;
                })
                .collect(Collectors.toList());
        System.out.println("Conversation DTOs: " + conversationDTOs);
        return conversationDTOs;
    }
}

package org.skillswap.skillswapbackend.dto;

import lombok.Data;
import org.skillswap.skillswapbackend.Models.User;

@Data
public class ConversationDTO {
    private UserDTO withUser;
    private MessageDTO lastMessage;
}

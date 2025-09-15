package org.skillswap.skillswapbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.skillswap.skillswapbackend.Models.User;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConversationDTO {
    private UserDTO withUser;
    private MessageDTO lastMessage;
}

package org.skillswap.skillswapbackend.Models;

import lombok.*;

import java.awt.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Message {
    private String content;
    private String sender;
    private MessageType type;
}
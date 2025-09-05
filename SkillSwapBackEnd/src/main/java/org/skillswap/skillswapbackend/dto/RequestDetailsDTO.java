
package org.skillswap.skillswapbackend.dto;

import lombok.Data;

@Data
public class RequestDetailsDTO {
    private Long id;
    private UserDTO requester;
    private UserDTO receiver;
    private SkillDTO skill;
    private String status;
}

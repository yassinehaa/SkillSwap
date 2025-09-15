
package org.skillswap.skillswapbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestDetailsDTO {
    private Long id;
    private UserDTO requester;
    private UserDTO receiver;
    private SkillDTO skill;
    private String status;
}
